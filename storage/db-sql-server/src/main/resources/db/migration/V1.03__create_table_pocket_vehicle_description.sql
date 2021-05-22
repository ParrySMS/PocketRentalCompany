-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_vehicle_description]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_vehicle_description]
GO

CREATE TABLE [pocket].[pocket_vehicle_description]
(
	[id]                INTEGER IDENTITY(1,1)   NOT NULL,
	[vehicle_id]        INTEGER                 NOT NULL,
	[description]       VARCHAR(2016)           NOT NULL,
	[create_on]         DATETIME                NOT NULL,
	[last_modified]     DATETIME                NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_vehicle_description]
	ADD CONSTRAINT [pk_pocket_vehicle_description] PRIMARY KEY CLUSTERED ([id] ASC)
GO

