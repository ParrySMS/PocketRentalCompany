USE [demo]
GO

TRUNCATE TABLE [pocket].[pocket_price_frequency]

INSERT [pocket].[pocket_price_frequency] (frequency_type, create_on, last_modified) VALUES ('Daily', GETDATE(), GETDATE())  -- id:1
INSERT [pocket].[pocket_price_frequency] (frequency_type, create_on, last_modified) VALUES ('Weekly', GETDATE(), GETDATE())  -- id:2
INSERT [pocket].[pocket_price_frequency] (frequency_type, create_on, last_modified) VALUES ('Monthly', GETDATE(), GETDATE())  -- id:3
INSERT [pocket].[pocket_price_frequency] (frequency_type, create_on, last_modified) VALUES ('Half-Yearly', GETDATE(), GETDATE())  -- id:4
INSERT [pocket].[pocket_price_frequency] (frequency_type, create_on, last_modified) VALUES ('Yearly', GETDATE(), GETDATE())  -- id:5
GO
