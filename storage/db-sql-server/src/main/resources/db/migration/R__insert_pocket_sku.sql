USE [demo]
GO

TRUNCATE TABLE [pocket].[pocket_sku]

-- Toyota
INSERT [pocket].[pocket_sku] (sku_guid, vehicle_id, color, create_on, last_modified)
    VALUES ('acc35760-4048-437f-b794-fa323f5d0105', 1, 'MIDNIGHT BLACK METALLIC', GETDATE(), GETDATE())  -- id:1

INSERT [pocket].[pocket_sku] (sku_guid, vehicle_id, color, create_on, last_modified)
    VALUES ('a4eda6e7-35e5-42f8-99a3-7150bfd70b00', 1, 'SUPERSONIC RED', GETDATE(), GETDATE())  -- id:2

-- BWM
INSERT [pocket].[pocket_sku] (sku_guid, vehicle_id, color, create_on, last_modified)
    VALUES ('bkq29752-b794-4703-541j-fa3d6h9l0105', 2, 'SUPER WHITE', GETDATE(), GETDATE())  -- id:3

INSERT [pocket].[pocket_sku] (sku_guid, vehicle_id, color, create_on, last_modified)
    VALUES ('b7155a02-4558-4956-bba9-d77e9e75ca32', 2, 'BLUEPRINT', GETDATE(), GETDATE())  -- id:4

GO
