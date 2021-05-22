-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO 

IF EXISTS (
		SELECT *
		FROM sys.objects
		WHERE object_id = OBJECT_ID(N'[pocket].[pocket_client]')
			AND type in (N'U')
) DROP TABLE [pocket].[pocket_client]
GO 

CREATE TABLE [pocket].[pocket_client]
(
    [id]                INTEGER IDENTITY(1, 1)      NOT NULL,
	[name]              VARCHAR(126)                NOT NULL,
	[contact]           VARCHAR(16)                 NOT NULL,
	[citizen_id]        VARCHAR(32)                 NOT NULL,
	[create_on]         DATETIME                    NOT NULL,
	[last_modified]     DATETIME                    NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_client]
ADD CONSTRAINT [pk_pocket_client] PRIMARY KEY CLUSTERED ([id] ASC)
GO