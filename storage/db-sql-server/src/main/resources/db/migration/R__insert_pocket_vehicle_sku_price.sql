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
    VALUES ('006ca2f8-a8df-43cc-b85e-59208b3635bc', 1, '180.00', GETDATE(), GETDATE()) -- Daily

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('006ca2f8-a8df-43cc-b85e-59208b3635bc', 2, '1230.00', GETDATE(), GETDATE()) -- Weekly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('006ca2f8-a8df-43cc-b85e-59208b3635bc', 3, '4420.00', GETDATE(), GETDATE()) -- Monthly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('006ca2f8-a8df-43cc-b85e-59208b3635bc', 4, '25000.00', GETDATE(), GETDATE()) -- Half-Yearly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('006ca2f8-a8df-43cc-b85e-59208b3635bc', 5, '50000.00', GETDATE(), GETDATE()) -- Yearly


-- BWM 4
INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('341dbdc5-4a62-48ed-a146-e86078e949a2', 1, '210.00', GETDATE(), GETDATE()) -- Daily

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('341dbdc5-4a62-48ed-a146-e86078e949a2', 2, '1300.00', GETDATE(), GETDATE()) -- Weekly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('341dbdc5-4a62-48ed-a146-e86078e949a2', 3, '4650.00', GETDATE(), GETDATE()) -- Monthly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('341dbdc5-4a62-48ed-a146-e86078e949a2', 4, '26500.00', GETDATE(), GETDATE()) -- Half-Yearly

INSERT [pocket].[pocket_sku_price] (sku_guid, price_frequency_id, price, create_on, last_modified)
    VALUES ('341dbdc5-4a62-48ed-a146-e86078e949a2', 5, '51800.00', GETDATE(), GETDATE()) -- Yearly

GO