USE [demo]
GO

TRUNCATE TABLE [pocket].[pocket_sku_price]

-- Toyota 1
INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('acc35760-4048-437f-b794-fa323f5d0105', 1, '200.00', GETDATE(), GETDATE()) -- Daily

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('acc35760-4048-437f-b794-fa323f5d0105', 2, '1250.00', GETDATE(), GETDATE()) -- Weekly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('acc35760-4048-437f-b794-fa323f5d0105', 3, '4500.00', GETDATE(), GETDATE()) -- Monthly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('acc35760-4048-437f-b794-fa323f5d0105', 4, '25560.00', GETDATE(), GETDATE()) -- Half-Yearly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('acc35760-4048-437f-b794-fa323f5d0105', 5, '51100.00', GETDATE(), GETDATE()) -- Yearly

-- Toyota 2
INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('a4eda6e7-35e5-42f8-99a3-7150bfd70b00', 1, '190.00', GETDATE(), GETDATE()) -- Daily

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('a4eda6e7-35e5-42f8-99a3-7150bfd70b00', 2, '1240.00', GETDATE(), GETDATE()) -- Weekly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('a4eda6e7-35e5-42f8-99a3-7150bfd70b00', 3, '4450.00', GETDATE(), GETDATE()) -- Monthly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('a4eda6e7-35e5-42f8-99a3-7150bfd70b00', 4, '25500.00', GETDATE(), GETDATE()) -- Half-Yearly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('a4eda6e7-35e5-42f8-99a3-7150bfd70b00', 5, '51000.00', GETDATE(), GETDATE()) -- Yearly


-- BWM 3
INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('bkq29752-b794-4703-541j-fa3d6h9l0105', 1, '180.00', GETDATE(), GETDATE()) -- Daily

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('bkq29752-b794-4703-541j-fa3d6h9l0105', 2, '1230.00', GETDATE(), GETDATE()) -- Weekly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('bkq29752-b794-4703-541j-fa3d6h9l0105', 3, '4420.00', GETDATE(), GETDATE()) -- Monthly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('bkq29752-b794-4703-541j-fa3d6h9l0105', 4, '25000.00', GETDATE(), GETDATE()) -- Half-Yearly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('bkq29752-b794-4703-541j-fa3d6h9l0105', 5, '50000.00', GETDATE(), GETDATE()) -- Yearly


-- BWM 4
INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('b7155a02-4558-4956-bba9-d77e9e75ca32', 1, '210.00', GETDATE(), GETDATE()) -- Daily

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('b7155a02-4558-4956-bba9-d77e9e75ca32', 2, '1300.00', GETDATE(), GETDATE()) -- Weekly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('b7155a02-4558-4956-bba9-d77e9e75ca32', 3, '4650.00', GETDATE(), GETDATE()) -- Monthly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('b7155a02-4558-4956-bba9-d77e9e75ca32', 4, '26500.00', GETDATE(), GETDATE()) -- Half-Yearly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('b7155a02-4558-4956-bba9-d77e9e75ca32', 5, '51800.00', GETDATE(), GETDATE()) -- Yearly

GO