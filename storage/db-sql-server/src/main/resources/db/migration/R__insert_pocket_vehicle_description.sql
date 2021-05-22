USE [demo]
GO

TRUNCATE TABLE [pocket].[pocket_vehicle_description]

-- Toyota
INSERT [pocket].[pocket_vehicle_description] (vehicle_id, description, create_on, last_modified)
    VALUES (
        1,
        "The powerful, stylish 2021 Camry Hybrid gives up nothing with its optimized fuel economy, 79 advanced tech and stirring drive. We have expanded our hybrid battery warranty to reflect our confidence in the quality, dependability and reliability of our products. From the 2020 model year forward, every Toyota hybrid battery warranty will cover 10 years from date of first use or 150,000 miles, whichever comes first (previously 8 years or 100,000 miles).",
        GETDATE(),
        GETDATE()
    )

-- BWM
INSERT [pocket].[pocket_vehicle_description] (vehicle_id, description, create_on, last_modified)
    VALUES (
        2,
        "What it is: The BMW 6 Series comes in two body styles: convertible or four-door Gran Coupe. The traditional two-door coupe body style has been dropped for 2018. Six-cylinder or V-8 power is available along with rear- or all-wheel drive. The top performer in the line is the high-performance M6, which is available with a manual transmission to please sports car traditionalists. Competitors include the Audi A7, Jaguar F-Type and Mercedes-Benz SL-Class."
        GETDATE(),
        GETDATE()
    )
GO
