USE [RentShop]
GO


if object_id('dbo.RentOrderAdd') is null begin
    exec('create procedure dbo.RentOrderAdd as return 0') ;
end
go
 
print 'Altering stored procedure dbo.RentOrderAdd' ;
go
 
alter procedure dbo.RentOrderAdd
(
    @pProductItemID       int,
    @pRentPeriod          int,
    @pClientID            int
)

as

 
set nocount on ;
set xact_abort on ;
 
---------------------------------------------------------------------
-- Declarations
---------------------------------------------------------------------
declare  -- Standard error variables.
    @MsgParm1           varchar(200)
  , @MsgParm2           varchar(200)
  , @ErrorMessagePrefix nvarchar(40)   = ''
  , @ErrorMessage       nvarchar(4000) = 'Unknown'
  , @ErrorNumber        int         = 200106  
  , @ErrorSeverity      int            = 16
  , @ErrorState         int            = 1
  , @ErrorLine          int            = 0
  , @FALSE              tinyint        = 0
  , @TRUE               tinyint        = 1
  , @RC_FAILURE         int            = -100                    -- Return code:  FAILURE.
  , @RC_SUCCESS         int            = 0                      -- Return code:  SUCCESS.
;
 
declare  -- Stored procedure specific constants and variables.
    --@SystemUser         sysname        = system_user,
    @ProcedureName      sysname        = object_name( @@PROCID ), -- The name of this stored procedure.
    @ClientID           int,
    @ProductItemID      int,
    @CreateDate         datetime =  current_timestamp, 
    @EndRentDate        date,
    @OderedRentStatus   int = 1, -- 1 means in use
    @Status             int
    
    declare  -- Standard variables (transaction handling).
    @ExitCode            int            = @RC_SUCCESS,  
    @TrancountSave      int,
    @SavePointName      nvarchar(64)   = '$' + cast( @@NestLevel as varchar(15) )
                                       + '_' + @ProcedureName,
    @TranStartedBool    tinyint        = @FALSE 


;


---------------------------------------------------------------------
-- Try Block
---------------------------------------------------------------------
begin try

---------------------------------------------------------------------
-- Validations
---------------------------------------------------------------------
 
-- Check parameters that don't allow nulls

select @MsgParm1 =
    case
    when @pProductItemID is null then ' ProductItemID '
    when @pRentPeriod    is null then ' RentPeriod '
    when @pClientID      is null then ' ClientID'

    else NULL
end ;
    
if @MsgParm1 is not null begin
		select @MsgParm2    = 'NULL or blank ';   
		select @ProcedureName = @ProcedureName + ' ' + @MsgParm1 + @MsgParm2;
		raiserror (@ProcedureName, @ErrorSeverity, @ErrorState) ;
end;
 
 
select @ClientID = ClientID
from   dbo.Client with(nolock) 
where  ClientID = @pClientID ;

   
if (@ClientID is null) begin;
	select @MsgParm1    =  ' ClientID ', 
	@MsgParm2    = '@pClientID=' + CAST(@pClientID as varchar (100))+ ' does not exist in dbo.Client table';
	select @ProcedureName = @ProcedureName + @MsgParm1+ @MsgParm2;
	raiserror (@ProcedureName, @ErrorSeverity, @ErrorState) ;
end;
 
 select @ProductItemID = ProductItemID, @Status = Status
 from dbo.ProductItem 
 where ProductItemID = @pProductItemID;
 
 
 if (@ProductItemID is null) begin;
	select @MsgParm1    =  ' ProductItemID ', 
	@MsgParm2    = 'ProductItemID=' + cast (@pProductItemID as varchar) + ' does not exist in dbo.ProductItem table';
	select @ProcedureName = @ProcedureName + @MsgParm1+ @MsgParm2;
	raiserror (@ProcedureName, @ErrorSeverity, @ErrorState) ;
end;

 if (@Status!= 0) begin;
	select @MsgParm1    =  ' ProductItemID ', 
	@MsgParm2    = 'ProductItemID=' + cast (@pProductItemID as varchar) + ' is not available to rent. Status is wrong.';
	select @ProcedureName = @ProcedureName + @MsgParm1+ @MsgParm2;
	raiserror (@ProcedureName, @ErrorSeverity, @ErrorState) ;
end;
---------------------------------------------------------------------
-- Processing
---------------------------------------------------------------------

select @TrancountSave = @@Trancount ;
if (@TrancountSave = 0) 
     begin transaction @SavePointName ;
else save transaction  @SavePointName ;


select @ErrorMessagePrefix = 'on dbo.RentOrder INSERT - ' ;
select  @EndRentDate = dateadd (day, @pRentPeriod, @CreateDate);

insert into dbo.RentOrder 
		(ProductItemID,
		 StartRentDate,
		 EndRentDate,
		 ActualEndDate,
		 RentAmount,
		 RentStatus,
		 ClientID,
		 CreateDate,
		 ModifyDate
		)
select
        @pProductItemID,
        @CreateDate as StartRentDate,
        @EndRentDate,
        NULL as ActualEndDate,
		@pRentPeriod *(ProductBasePrice - (ProductBasePrice * WearRate)*0.01),
		@OderedRentStatus,
		@ClientID,
		@CreateDate,
		@CreateDate as  ModifyDate
from dbo.Product p join dbo.ProductItem pi on p.ProductID = pi.ProductID
where pi.ProductItemID = @pProductItemID;

select @ErrorMessagePrefix = 'on dbo.ProductItem UPDATE - ' ;
update dbo.ProductItem 
set Status = @OderedRentStatus
where ProductItemID = @pProductItemID;

select @ErrorMessagePrefix = 'on dbo.Product UPDATE - ' ;
update dbo.Product 
set AvailableQuantity = AvailableQuantity - 1
from Product p INNER JOIN ProductItem pi ON pi.ProductID = p.ProductID 
where ProductItemID = @pProductItemID;

if (@TrancountSave = 0) commit transaction @SavePointName ;    
              
---------------------------------------------------------------------
-- Catch block
---------------------------------------------------------------------
end try
    begin catch
  if (@TranStartedBool = @TRUE) begin;
       if @trancountsave = 0
         rollback transaction ;
       else begin ;
            if xact_state() <> -1 
                rollback transaction @savepointname ;
            else
                rollback transaction ;
        end ;
                                  end;             
                             
        select @ExitCode      = @RC_FAILURE
             , @ErrorMessage  = @ErrorMessagePrefix + SPACE(1) + error_message()
             , @ErrorNumber   = error_number()
             , @ErrorSeverity = error_severity()
             , @ErrorState    = error_state()
             , @ErrorLine     = error_line()
        ;   

RAISERROR ('Msg %d, Line %d: %s',
@ErrorSeverity,
@ErrorState,
@ErrorNumber,
@ErrorLine,
@ErrorMessage);

RETURN @ErrorNumber;

end catch
 

go
 
 

/*

set nocount on;
declare @rc int,
        @ProductItemID int = 7,
        @RentPeriod    int = 12,
        @ClientID    int = null
        

begin tran
select * from dbo.Product p INNER JOIN ProductItem pi ON pi.ProductID = p.ProductID 
where ProductItemID = @ProductItemID;
select * from dbo.ProductItem where ProductItemID = @ProductItemID; 
select * from dbo.RentOrder where ProductItemID = @ProductItemID; 

exec @rc = dbo.RentOrderAdd @pProductItemID = @ProductItemID,
    @pRentPeriod = @RentPeriod,
    @pClientID = @ClientID

select * from dbo.Product p INNER JOIN ProductItem pi ON pi.ProductID = p.ProductID 
where ProductItemID = @ProductItemID;
select * from dbo.ProductItem where ProductItemID = @ProductItemID; 
select * from dbo.RentOrder where ProductItemID = @ProductItemID; 

rollback tran

*/