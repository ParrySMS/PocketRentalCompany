-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_sku_price]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_sku_price]
GO

CREATE TABLE [pocket].[pocket_sku_price]
(
	[id]                    INTEGER IDENTITY(1,1)   NOT NULL,
	[sku_guid]              VARCHAR(63)             NOT NULL,
	[price_frequency_id]    INTEGER                NOT NULL,
	[price]                 VARCHAR(63)             NOT NULL,
	[create_on]             DATETIME                NOT NULL,
	[last_modified]         DATETIME                NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_sku_price]
	ADD CONSTRAINT [pk_pocket_sku_price] PRIMARY KEY CLUSTERED ([id] ASC)
GO
