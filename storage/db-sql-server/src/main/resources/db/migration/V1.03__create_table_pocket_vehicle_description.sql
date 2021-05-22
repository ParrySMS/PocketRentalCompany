-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_vehicle_stock]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_vehicle_stock]
GO

CREATE TABLE [pocket].[pocket_vehicle_stock]
(
	[id]                INTEGER IDENTITY(1,1)   NOT NULL,
	[sku_guid]          VARCHAR(63)             NOT NULL,
	[vehicle_id]        INTEGER                 NOT NULL,
	[service_day_count] INTEGER                 NOT NULL,
	[free_day_count]    INTEGER                 NOT NULL,
	[create_on]         DATETIME                NOT NULL,
	[last_modified]     DATETIME                NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_vehicle_stock]
	ADD CONSTRAINT [pk_pocket_vehicle_stock] PRIMARY KEY CLUSTERED ([id] ASC)
GO
