USE [demo]
GO

TRUNCATE TABLE [pocket].[pocket_vehicle_description]

-- Toyota
INSERT [pocket].[pocket_vehicle_description] (vehicle_id, description, create_on, last_modified)
    VALUES (
        1,
        'The powerful, stylish 2021 Camry Hybrid gives up nothing with its optimized fuel economy, 79 advanced tech and stirring drive.',
        GETDATE(),
        GETDATE()
    )

-- BWM
INSERT [pocket].[pocket_vehicle_description] (vehicle_id, description, create_on, last_modified)
    VALUES (
        2,
        'What it is: The BMW 6 Series comes in two body styles: convertible or four-door Gran Coupe. The traditional two-door coupe body style has been dropped for 2018.',
        GETDATE(),
        GETDATE()
    )
GO
