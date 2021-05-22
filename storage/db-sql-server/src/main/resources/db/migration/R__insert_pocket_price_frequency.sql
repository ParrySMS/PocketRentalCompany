USE [demo]
GO

TRUNCATE TABLE [pocket].[pocket_price_frequency]

INSERT [pocket].[pocket_price_frequency] (id, frequency_type, create_on, last_modified) VALUES ( 1, 'Daily', GETDATE(), GETDATE())
INSERT [pocket].[pocket_price_frequency] (id, frequency_type, create_on, last_modified) VALUES ( 2, 'Weekly', GETDATE(), GETDATE())
INSERT [pocket].[pocket_price_frequency] (id, frequency_type, create_on, last_modified) VALUES ( 3, 'Monthly', GETDATE(), GETDATE())
INSERT [pocket].[pocket_price_frequency] (id, frequency_type, create_on, last_modified) VALUES ( 4, 'Half-Yearly', GETDATE(), GETDATE())
INSERT [pocket].[pocket_price_frequency] (id, frequency_type, create_on, last_modified) VALUES ( 5, 'Yearly', GETDATE(), GETDATE())
GO

DBCC CHECKIDENT ([pocket].[pocket_price_frequency],reseed,5)
GO

INSERT [pocket].[pocket_price_frequency] (frequency_type, create_on, last_modified) VALUES ('test', GETDATE(), GETDATE())
GO