-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_vehicle]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_vehicle]
GO

CREATE TABLE [pocket].[pocket_vehicle]
(
	[id]                INTEGER IDENTITY(1,1)   NOT NULL,
	[name]              VARCHAR(126)            NOT NULL,
	[model_year]        INTEGER                 NOT NULL,
	[create_on]         DATETIME                NOT NULL,
	[last_modified]     DATETIME                NOT NULL
) ON [demo]
GO

ALTER TABLE [demo].[pocket].[pocket_vehicle]
	ADD CONSTRAINT [pk_pocket_vehicle] PRIMARY KEY CLUSTERED ([id] ASC)
GO
