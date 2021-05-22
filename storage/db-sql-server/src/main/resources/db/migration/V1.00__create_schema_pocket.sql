USE [demo]
GO

IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'pocket')
EXEC sp_executesql N'CREATE SCHEMA [pocket] authorization dbo'
GO