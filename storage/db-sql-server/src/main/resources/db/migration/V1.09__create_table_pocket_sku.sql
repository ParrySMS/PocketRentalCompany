-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_sku]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_sku]
GO

CREATE TABLE [pocket].[pocket_sku]
(
	[id]              INTEGER IDENTITY(1,1)   NOT NULL,
	[sku_guid]        VARCHAR(127)            NOT NULL,
	[vehicle_id]      INTEGER                 NOT NULL,
	[color]           VARCHAR(127)            NOT NULL,
	[create_on]       DATETIME                NOT NULL,
	[last_modified]   DATETIME                NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_sku]
	ADD CONSTRAINT [pk_pocket_sku] PRIMARY KEY CLUSTERED ([id] ASC)
GO
