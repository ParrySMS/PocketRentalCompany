-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_price_frequency]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_price_frequency]
GO

CREATE TABLE [pocket].[pocket_price_frequency]
(
	[id]                INTEGER IDENTITY(1,1)   NOT NULL,
	[frequency_type]    VARCHAR(63)             NOT NULL,
	[create_on]         DATETIME                NOT NULL,
	[last_modified]     DATETIME                NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_price_frequency]
	ADD CONSTRAINT [pk_pocket_price_frequency] PRIMARY KEY CLUSTERED ([id] ASC)
GO
