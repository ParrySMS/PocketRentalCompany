-- noinspection SqlNoDataSourceInspectionForFile
USE [demo]
GO

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[pocket].[pocket_rental_order]') AND type in (N'U'))
DROP TABLE [pocket].[pocket_rental_order]
GO

CREATE TABLE [pocket].[pocket_rental_order]
(
	[id]              INTEGER IDENTITY(1,1)   NOT NULL,
	[client_id]       INTEGER             NOT NULL,
	[signing_time]    DATETIME                NOT NULL,
	[total_price]     VARCHAR(63)                NOT NULL,
	[create_on]       DATETIME                NOT NULL,
	[last_modified]   DATETIME                NOT NULL
)
GO

ALTER TABLE [demo].[pocket].[pocket_rental_order]
	ADD CONSTRAINT [pk_pocket_rental_order] PRIMARY KEY CLUSTERED ([id] ASC)
GO
