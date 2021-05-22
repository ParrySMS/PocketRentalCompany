USE [demo]
GO

TRUNCATE TABLE [pocket].[pocket_vehicle]
INSERT [pocket].[pocket_vehicle] (name, model_year, create_on, last_modified) VALUES ('Toyota Camry', 2021, GETDATE(), GETDATE())  -- id:1
INSERT [pocket].[pocket_vehicle] (name, model_year, create_on, last_modified) VALUES ('BMW 650', 2018, GETDATE(), GETDATE())  -- id:2
GO
