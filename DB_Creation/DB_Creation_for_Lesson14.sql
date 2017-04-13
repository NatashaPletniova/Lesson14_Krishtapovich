
USE [master]
GO


IF  EXISTS (SELECT name FROM sys.databases WHERE name = N'RentShop')
DROP DATABASE [RentShop]
GO

USE [master]
GO

CREATE DATABASE [RentShop]
GO


USE [RentShop]
GO


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[RentOrder]') AND type in (N'U'))
DROP TABLE [dbo].[RentOrder]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ProductItem]') AND type in (N'U'))
DROP TABLE [dbo].[ProductItem]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ProductItem]') AND type in (N'U'))
DROP TABLE [dbo].[ProductItem]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Client]') AND type in (N'U'))
DROP TABLE [dbo].[Client]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Product]') AND type in (N'U'))
DROP TABLE [dbo].[Product]
GO


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ProductCategory]') AND type in (N'U'))
DROP TABLE [dbo].[ProductCategory]
GO






CREATE TABLE [dbo].[ProductCategory](
	[ProductCategoryID] [int] NOT NULL,
	[Name] [nvarchar](50) NOT NULL,
	constraint PK_ProductCategory primary key (ProductCategoryID)
) ON [PRIMARY]

GO



CREATE TABLE [dbo].[Product](
	[ProductID] [int] NOT NULL,
	[ProductCategoryID] [int] NOT NULL,
	[ProductName] [nvarchar](100) NOT NULL,
	 ProductBasePrice money NOT NULL,
	[Quantity] [int] NOT NULL,
	[AvailableQuantity] [int] NOT NULL,
	constraint PK_Product primary key (ProductID)
) ON [PRIMARY]

GO


				
--SELECT ProductID, ProductCategoryID, ProductName,ProductBasePrice, Quantity FROM dbo.Product WHERE ProductName LIKE '%Bike%';

CREATE TABLE [dbo].[ProductItem](
	[ProductItemID] [int] NOT NULL IDENTITY,
	--[ItemNumber] [nvarchar](10) NOT NULL,
	[ProductID] [int] NOT NULL,
	[ManufacturingYear] [int] NULL,
	[ItemNote] [nvarchar](1000) NULL,
	[WearRate] [int] NOT NULL,
	[Status] [int] NOT NULL,
	[CreateDate] [datetime] NOT NULL,
	[ModifiedDate] [datetime] NOT NULL,
	constraint PK_ProductItemID primary key (ProductItemID)
) ON [PRIMARY]

GO


CREATE TABLE [dbo].[RentOrder](
	[RentOrderID] [int] NOT NULL IDENTITY,
	[ProductItemID] [int] NOT NULL,
	[StartRentDate] [date] NOT NULL,
	[EndRentDate] [date] NOT NULL,
	[ActualEndDate] [date] NULL,
	[RentAmount] [money] NOT NULL,
	[RentStatus] [int] NOT NULL,
	[ClientID] [int] NOT NULL,
	[CreateDate] [datetime] NOT NULL,
	[ModifyDate] [datetime] NOT NULL,
	constraint PK_RentOrder primary key (RentOrderID)
) ON [PRIMARY]

GO



CREATE TABLE [dbo].[Client](
	[ClientID] [int] NOT NULL,
	[FirstName] [nvarchar](50) NULL,
	[LastName] [nvarchar](50) NULL,
	[DocumentID] [nvarchar](50) NULL,
 CONSTRAINT [PK_Client] PRIMARY KEY  ([ClientID])
 )ON [PRIMARY]
 
GO



/*
if object_id('akItemNumber') is null begin;
    print 'Creating alternate key constraint dbo.ItemNumber.';

    alter table dbo.ProductItem
       add constraint akProductItem_ItemNumber unique (ItemNumber);
end;
go
*/
if object_id('akProductItemID_RentStatus_StartRentDate') is null begin;
    print 'Creating alternate key constraint dbo.akProductItemID_RentStatus_StartRentDate .';

    alter table dbo.RentOrder
       add constraint akProductItemID_RentStatus_StartRentDate 
        unique (ProductItemID ASC, RentStatus ASC, StartRentDate ASC);
end;
go


if object_id('akDocumentID') is null begin;
    print 'Creating alternate key constraint dbo.DocumentID.';

    alter table dbo.Client
       add constraint akDocumentID unique  (DocumentID);
end;
go



if object_id('dbo.fkProduct_ProductCategory_ProductCategoryID') is null begin;
    print 'Creating foreign key constraint dbo.fkProduct_ProductCategory_ProductCategoryID.';
    alter table dbo.Product
       add constraint fkProduct_ProductCategory_ProductCategoryID foreign key (ProductCategoryID)
          references dbo.ProductCategory (ProductCategoryID);
end;
go



if object_id('dbo.fkProductItem_Product_ProductID') is null begin;
    print 'Creating foreign key constraint dbo.fkProductItem_Product_ProductID.';
    alter table dbo.ProductItem
       add constraint fkProductItem_Product_ProductID foreign key (ProductID)
          references dbo.Product (ProductID);
end;
go


if object_id('dbo.fkRentOrder_ProductItem_ProductItemID') is null begin;
    print 'Creating foreign key constraint dbo.fkRentOrder_ProductItem_ProductItemID.';
    alter table dbo.RentOrder
       add constraint fkRentOrder_ProductItem_ProductItemID foreign key (ProductItemID)
          references dbo.ProductItem (ProductItemID);
end;
go


if object_id('dbo.fkRentOrder_Client_ClientID') is null begin;
    print 'Creating foreign key constraint dbo.fkRentOrder_Client_ClientID.';
    alter table dbo.RentOrder
       add constraint fkRentOrder_Client_ClientID foreign key (ClientID)
          references dbo.Client(ClientID);
end;
go



INSERT INTO [RentShop].[dbo].[Client]
           ([ClientID]
           ,[FirstName]
           ,[LastName]
           ,[DocumentID])
VALUES
(1 ,'Ivan', 'Ivanov', '12345678'),
(2 ,'Bob', 'Smith',   'A1345RTY'),
(3 ,'Trac','Cao',     'GHU78990')
GO

INSERT INTO [RentShop].[dbo].[ProductCategory]
           ([ProductCategoryID]
           ,[Name])
VALUES
           (1, 'Bikes'),
           (2, 'Clothing'),
           (3, 'Accessories')
           
GO



INSERT INTO [RentShop].[dbo].[Product]
           ([ProductID]
           ,[ProductCategoryID]
           ,[ProductName]
           ,ProductBasePrice
           ,[Quantity]
           ,[AvailableQuantity])
     VALUES
           (1, 1, 'Bike Wash Dissolver',50,5, 5),
           (2, 1, 'Mountain Bike Socks', 100, 3, 2),
           (3, 2, 'Knee pad',  5, 1, 1),
           (4, 1, 'All Purpose Bike Stand', 20, 2, 2),
           (5, 2, 'Windbreaker',  6, 1, 1),
           (6, 3, 'Navigator',  10, 3, 3),
           (7, 3, 'Pulsometer',  3, 1, 1);
           
 
GO

-- select * from [RentShop].[dbo].[ProductItem];
INSERT INTO [RentShop].[dbo].[ProductItem]
           ([ProductID]
           ,[ManufacturingYear]
           ,[ItemNote]
           ,[WearRate]
           ,[Status]
           ,[CreateDate]
           ,[ModifiedDate])
     VALUES
           (1,2016, 'This is the model with tyre''s enhancement',10,0,'2016-03-22 20:51:09.110' ,'2016-03-22 20:51:09.110'),
           (1,2015, 'This is the model with spotlamp',40,0,'2015-09-22 20:51:09.110', '2015-09-22 20:51:09.110'),
           (1,2017, 'This is the model with spotlamp',5,0,'2016-09-22 20:51:09.110' , '2016-09-22 20:51:09.110'),
           (1,2017, 'This is the model with spotlamp',50,0,'2016-09-22 20:51:09.110' , '2016-09-22 20:51:09.110'),
           (1,2017, 'This is the model with spotlamp',5,0,'2016-09-22 20:51:09.110' , '2016-09-22 20:51:09.110'),
           
           (2,2016, 'For crazy jumping',4,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           (2,2016, 'For crazy jumping',4,1,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           (2,2017, 'For jumping',5,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           (3,2015, 'Knee-pad size M',5,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           
           (4,2017, '',5,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           (4,2017, '',5,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
          
           (5,2017, '',50,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           
           (6,2017, 'CPRS 12-3456',30,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           (6,2017, 'CPRS 12-3456',50,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110'),
           (6,2017, 'CPRS 12-3456',50,0,'2016-09-22 20:51:09.110' ,'2016-09-22 20:51:09.110')
GO


