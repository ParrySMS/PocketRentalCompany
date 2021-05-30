# Software Design Document

## User Story

- [] sign up/sign in
- [] view the most popular sku
- [view all vehicle](https://github.com/ParrySMS/PocketRentalCompany/issues/11)
- [view all SKUs for one vehicle](https://github.com/ParrySMS/PocketRentalCompany/issues/12)
- [v] view available vehicle for selected rental period.
- [v] choose one sku and show the price, change rental period and show the new price
- [v] add the {SKU + period} into order
- [v] show order
- [] view other SKU in the same rental period
- [] take an order, waiting paid
- [] finish payment

## Frontend
TBD

## Backend
Java SpringBoot RESTful API Service on Azure
- SpringBoot `2.5.0`
- Java `JDK11`
- MyBatis `3.4.1`

Flyway Service for database migration

- Flyway `7.5.2`

### API Design

| Feature                                      | url                                                          |
| -------------------------------------------- | ------------------------------------------------------------ |
| get all vehicles                             | GET /vehicles/{offset}/{pageSize}                            |
| get all SKUs for one vehicle                 | GET   /vehicles/{vehicleId}/skus                             |
| get all SKUs for one vehicle                 | GET   /vehicles/{vehicleId}/skus/{offset}/{pageSize}         |
| get available vehicles in selected period    | GET    /vehicles?startDate={startDate}&endDate={endDate}     |
| get the price for one SKU in selected period | GET    /vehicles/{vehiclesId}/sku/{skuId}/price?startDate={startDate}&endDate={endDate} |
|                                              |                                                              |
| create an order for a SKU in selected period | POST /orders                                                 |
| add other SKU in other selected period       | PUT /orders/{orderId}                                        |
| update the original rental schedule          | PUT /orders/{orderId}/{rentalScheduleId}                     |
| list one order                               | GET /orders/{orderId}/client/{clientId}                      |
| list all orders                              | GET /orders/client/{clientId}                                |


## Database
- SQL Server `Microsoft SQL Azure (RTM) - 12.0.2000.8`

### DB Design


| pocket_client |      |           |             |                         |                         |
| ------------- | ---- | --------- | ----------- | ----------------------- | ----------------------- |
| id            | name | contact   | citizen_id  | create_on               | last_modified           |
| 1             | Tom  | 159221141 | 44502316231 | 2021-05-22 12:24:45.097 | 2021-05-22 12:24:45.097 |

| pocket_vehicle |      |            |           |               |
| -------------- | ---- | ---------- | --------- | ------------- |
| id             | name | model_year | create_on | last_modified |
|1	|Toyota Camry|	2021	|2021-05-22 12:17:26.873|	2021-05-22 12:17:26.873|
|2|BMW 650	|2018	|2021-05-22 12:17:26.873|	2021-05-22 12:17:26.873|

| pocket_vehicle_description |            |             |           |               |
| -------------------------- | ---------- | ----------- | --------- | ------------- |
| id                         | vehicle_id | description | create_on | last_modified |
| 1    | 1          | The powerful, stylish 2021 Camry Hybrid gives up nothing with its   optimized fuel economy, 79 advanced tech and stirring drive. | 2021-05-22 12:17:26.873 | 2021-05-22 12:17:26.873      |
| 2    | 2          | What it is: The BMW 6 Series comes in two body styles: convertible or   four-door Gran Coupe. The traditional two-door coupe body style has been   dropped for 2018. | 2021-05-22 12:17:26.873   | 2021-05-22 12:17:26.873   |

| pocket_vehicle_stock |          |            |                   |                |           |               |
| -------------------- | -------- | ---------- | ----------------- | -------------- | --------- | ------------- |
| id   | sku_guid                             | vehicle_id | service_day_count | free_day_count | create_on | last_modified |
| 1    | acc35760-4048-437f-b794-fa323f5d0105 | 1          | 0                 | 0              | 2021-05-22 12:17:26.873   |2021-05-22 12:17:26.873       |
| 2    | a4eda6e7-35e5-42f8-99a3-7150bfd70b00 | 1          | 0                 | 0              |2021-05-22 12:17:26.873    | 2021-05-22 12:17:26.873        |
| 3    | bkq29752-b794-4703-541j-fa3d6h9l0105 | 2          | 0                 | 0              | 2021-05-22 12:17:26.873    | 2021-05-22 12:17:26.873       |
| 4    | b7155a02-4558-4956-bba9-d77e9e75ca32 | 2          | 0                 | 0              | 2021-05-22 12:17:26.873    | 2021-05-22 12:17:26.873       |

| pocket_price_frequency |                 |           |               |
| ---------------------- | --------------- | --------- | ------------- |
| id   | frequency_type | create_on | last_modified |
| 1    | Daily          | 2021-05-22 12:17:26.873   | 2021-05-22 12:17:26.873       |
| 2    | Weekly         |2021-05-22 12:17:26.873  | 2021-05-22 12:17:26.873       |
| 3    | Monthly        | 2021-05-22 12:17:26.873   | 2021-05-22 12:17:26.873       |
| 4    | Half-Yearly    | 2021-05-22 12:17:26.873    | 2021-05-22 12:17:26.873       |
| 5    | Yearly         | 2021-05-22 12:17:26.873    | 2021-05-22 12:17:26.873        |

| pocket_rental_schedule |          |            |          |                |                 |           |               |
| ---------------------- | -------- | ---------- | -------- | -------------- | --------------- | --------- | ------------- |
| id                     | sku_guid | start_time | end_time | schedule_price | rental_order_id | create_on | last_modified |



| pocket_sku_price |          |                    |       |           |               |
| ---------------- | -------- | ------------------ | ----- | --------- | ------------- |
| id               | sku_guid | price_frequency_id | price | create_on | last_modified |
| 1    | acc35760-4048-437f-b794-fa323f5d0105 | 1    | 200  | 2021-5-22 12:24 | 2021-5-22 12:24 |
| 2    | acc35760-4048-437f-b794-fa323f5d0105 | 2    | 1250 | 2021-5-22 12:24 | 2021-5-22 12:24 |
| 3    | acc35760-4048-437f-b794-fa323f5d0105 | 3    | 4500 | 2021-5-22 12:24 | 2021-5-22 12:24 |
| 6    | a4eda6e7-35e5-42f8-99a3-7150bfd70b00 | 1    | 190  | 2021-5-22 12:24 | 2021-5-22 12:24 |
| 7    | a4eda6e7-35e5-42f8-99a3-7150bfd70b00 | 2    | 1240 | 2021-5-22 12:24 | 2021-5-22 12:24 |
| 8    | a4eda6e7-35e5-42f8-99a3-7150bfd70b00 | 3    | 4450 | 2021-5-22 12:24 | 2021-5-22 12:24 |

| pocket_rental_order |           |              |             |           |               |
| ------------------- | --------- | ------------ | ----------- | --------- | ------------- |
| id                  | client_id | signing_time | total_price | create_on | last_modified |



| pocket_sku          |           |              |       |           |               |
| ------------------- | --------- | ------------ | ----- | --------- | ------------- |
| id                  | sku_guid  | vehicle_id   | color | create_on | last_modified |
| 1    | acc35760-4048-437f-b794-fa323f5d0105 | 1          | MIDNIGHT BLACK METALLIC | 2021-5-22 12:17 | 2021-5-22 12:17 |
| 2    | a4eda6e7-35e5-42f8-99a3-7150bfd70b00 | 1          | SUPERSONIC RED          | 2021-5-22 12:17 | 2021-5-22 12:17 |
| 3    | bkq29752-b794-4703-541j-fa3d6h9l0105 | 2          | SUPER WHITE             | 2021-5-22 12:17 | 2021-5-22 12:17 |
| 4    | b7155a02-4558-4956-bba9-d77e9e75ca32 | 2          | BLUEPRINT               | 2021-5-22 12:17 | 2021-5-22 12:17 |
