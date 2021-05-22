-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_rental_schedule]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_rental_schedule]
GO

CREATE TABLE [pocket].[pocket_rental_schedule]
(
	[id]                INTEGER IDENTITY(1,1)   NOT NULL,
	[sku_guid]          VARCHAR(63)             NOT NULL,
	[start_time]        DATETIME                NOT NULL,
	[end_time]          DATETIME                NOT NULL,
	[schedule_price]    VARCHAR(63)             NOT NULL,
	[rental_order_id]   INTEGER                 NOT NULL,
	[create_on]         DATETIME                NOT NULL,
	[last_modified]     DATETIME                NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_rental_schedule]
	ADD CONSTRAINT [pk_pocket_rental_schedule] PRIMARY KEY CLUSTERED ([id] ASC)
GO
