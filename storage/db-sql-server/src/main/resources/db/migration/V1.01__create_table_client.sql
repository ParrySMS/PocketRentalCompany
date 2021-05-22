-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[demo].[pocket].[pocket_client]') AND type in (N'U'))
DROP TABLE [demo].[pocket].[pocket_client]
GO

CREATE TABLE [demo].[pocket].[pocket_client](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](126) NOT NULL,
	[contact] [varchar](16) NOT NULL,
	[citizen_id] [varchar](32) NOT NULL,
	[create_on] [datetime] NOT NULL,
	[last_modified] [datetime] NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_client]
	ADD CONSTRAINT [pk_pocket_client] PRIMARY KEY CLUSTERED ([id] ASC)
GO
