USE [RentShop]
GO


if object_id('dbo.AddProductItem') is null begin
    exec('create procedure dbo.AddProductItem as return 0') ;
end
go
 
print 'Altering stored procedure dbo.AddProductItem' ;
go
 
alter procedure dbo.AddProductItem
(
    @pProductID           int,
    @pManufacturingYear   int,
    @pItemNote            nvarchar(1000) = NULL,
    @pStatus              int = 0,
    @pWearRate            int,
    @pProductCategoryID   int = NULL,
    @pProductName         nvarchar(100) = NULL,
    @pProductBasePrice    int = NULL

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
    @ProductCategoryID   int,
    @ProductName         nvarchar(100),
    @Quantity            int = 1,
    @AvailableQuantity   int = 1

    
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
    when @pProductID     is null then ' ProductID '
    when @pManufacturingYear is null then ' ManufacturingYear '
    when @pWearRate     is null      then ' WearRate '
    when @pStatus     is null        then ' Status '
    else NULL
end ;
 
        
if @MsgParm1 is not null begin
		select @MsgParm2    = 'NULL or blank ';   
		select @ProcedureName = @ProcedureName + ' ' + @MsgParm1 + @MsgParm2;
		raiserror (@ProcedureName, @ErrorSeverity, @ErrorState) ;
end;
 
 
select @ProductName  = ProductName        
from   dbo.Product 
where  ProductID = @pProductID ;

   
if (@ProductName is null) begin;

select @MsgParm1 =
    case
    when @pProductCategoryID is null then ' ProductCategoryID '
    when @pProductName is null       then ' ProductName '
    when @pProductBasePrice is null  then ' ProductBasePrice '  
    else NULL
    end;
    
    if @MsgParm1 is not null begin
		select @MsgParm2    = 'NULL or blank ';   
		select @ProcedureName = @ProcedureName + ' ' + @MsgParm1 + @MsgParm2;
		raiserror (@ProcedureName, @ErrorSeverity, @ErrorState) ;
end;
end ;

---------------------------------------------------------------------
-- Processing
---------------------------------------------------------------------

select @TrancountSave = @@Trancount ;


if (@TrancountSave = 0) 
     begin transaction @SavePointName ;
else save transaction  @SavePointName ;

if (@ProductName is null) begin;
select @ErrorMessagePrefix = 'on dbo.Product INSERT - ' ;


insert into [dbo].[Product]
           ([ProductID]
           ,[ProductCategoryID]
           ,[ProductName]
           ,[ProductBasePrice]
           ,[Quantity]
           ,[AvailableQuantity])
values
			(@pProductID,
			@pProductCategoryID,
			@pProductName,
			@pProductBasePrice, 
			@Quantity,
			@AvailableQuantity);

select @ErrorMessagePrefix = 'on dbo.ProductItem INSERT - ' ;

insert into [dbo].[ProductItem]
           ([ProductID]
           ,[ManufacturingYear]
           ,[ItemNote]
           ,[WearRate]
           ,[Status]
           ,[CreateDate]
           ,[ModifiedDate])
values
           (@pProductID,
            @pManufacturingYear,
            @pItemNote, 
            @pWearRate,
            @pStatus,
            @CreateDate, 
            @CreateDate );
end;            
            

else  begin;
select @ErrorMessagePrefix = 'on dbo.ProductItem INSERT - ' ;

insert into [dbo].[ProductItem]
           ([ProductID]
           ,[ManufacturingYear]
           ,[ItemNote]
           ,[WearRate]
           ,[Status]
           ,[CreateDate]
           ,[ModifiedDate])
values
           (@pProductID,
            @pManufacturingYear,
            @pItemNote, 
            @pWearRate,
            @pStatus,
            @CreateDate, 
            @CreateDate );
            
select @ErrorMessagePrefix = 'on dbo.Product UPDATE - ' ;            
update dbo.Product set 
Quantity          = Quantity + 1,
AvailableQuantity = AvailableQuantity + 1 
where ProductID = @pProductID;

end;           

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
    @ProductID           int = 20,
    @ManufacturingYear   int = 2016,
    @ItemNote            nvarchar(1000) = 'Just added',
    @WearRate            int = 10,
    @Status              int = 0,
    @ProductCategoryID   int = NULL,
    @ProductName         nvarchar(100) = NULL,
    @ProductBasePrice    int = NULL

        

begin tran
select * from dbo.Product p where p.ProductID = @ProductID;
select * from dbo.ProductItem order by ProductItemID DESC;  


exec @rc = dbo.AddProductItem 
    @pProductID           = @ProductID, 
    @pManufacturingYear   = @ManufacturingYear,
    @pItemNote            = @ItemNote ,
    @pWearRate            = @WearRate, 
    @pStatus              = @Status,
    @pProductCategoryID   = @ProductCategoryID,
    @pProductName         = @ProductName,
    @pProductBasePrice    = @ProductBasePrice 

select * from dbo.Product p where p.ProductID = @ProductID;
select * from dbo.ProductItem order by ProductItemID DESC; 
rollback tran
*/
----------------------------------------------------------------
/*

set nocount on;
declare @rc int,
    @ProductID           int = 12,
    @ManufacturingYear   int = 2016,
    @ItemNote            nvarchar(1000) = 'Just added',
    @WearRate            int = 10,
    @Status              int = 0,
    @ProductCategoryID   int = 1,
    @ProductName         nvarchar(100) = 'New Product',
    @ProductBasePrice    int = 10

        

begin tran
select * from dbo.Product p where p.ProductID = @ProductID;
select * from dbo.ProductItem order by ProductItemID DESC;  


exec @rc = dbo.AddProductItem 
    @pProductID           = @ProductID, 
    @pManufacturingYear   = @ManufacturingYear,
    @pItemNote            = @ItemNote ,
    @pWearRate            = @WearRate, 
    @pStatus              = @Status,
    @pProductCategoryID   = @ProductCategoryID,
    @pProductName         = @ProductName,
    @pProductBasePrice    = @ProductBasePrice 

select * from dbo.Product p where p.ProductID = @ProductID;
select * from dbo.ProductItem order by ProductItemID DESC; 
rollback tran

*/