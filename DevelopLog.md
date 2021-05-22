

Deadline1：0:00 23/5/2021  11h
Deadline2：12:00 23/5/2021  23h 
Deadline3：24:00 23/5/2021  35h

# Doc 
## Free Resources
- https://portal.azure.com/#blade/Microsoft_Azure_Billing/FreeServicesBlade

## Server
- app-service: https://docs.microsoft.com/zh-cn/azure/app-service/quickstart-java?tabs=javase&pivots=platform-linux
- toolkit-for-intellij: https://docs.microsoft.com/zh-cn/azure/developer/java/toolkit-for-intellij/create-hello-world-web-app
- price: https://azure.microsoft.com/zh-cn/pricing/details/app-service/linux/

## Database     
- Azure SQL: https://azure.microsoft.com/zh-cn/products/azure-sql/database/
- NoSQL Azure Cosmos DB: https://azure.microsoft.com/en-us/services/cosmos-db/
- Draft table:

| pocket_client |      |         |            |           |               |
| ------------- | ---- | ------- | ---------- | --------- | ------------- |
| id            | name | contact | citizen_id | create_on | last_modified |

| pocket_vehicle |      |            |           |               |
| -------------- | ---- | ---------- | --------- | ------------- |
| id             | name | model_year | create_on | last_modified |

| pocketl_vehicle_description |            |             |           |               |
| --------------------------- | ---------- | ----------- | --------- | ------------- |
| id                          | vehicle_id | description | create_on | last_modified |

| pocket_vehicle_stock |          |            |       |                   |                |           |               |
| -------------------- | -------- | ---------- | ----- | ----------------- | -------------- | --------- | ------------- |
| id                   | sku_guid | vehicle_id | color | service_day_count | free_day_count | create_on | last_modified |

| pocket_rent_period |      |           |               |
| ------------------ | ---- | --------- | ------------- |
| id                 | type | create_on | last_modified |

| pocket_rental_schedule |          |            |          |                |                 |           |               |
| ---------------------- | -------- | ---------- | -------- | -------------- | --------------- | --------- | ------------- |
| id                     | sku_guid | start_time | end_time | schedule_price | rental_order_id | create_on | last_modified |

| pocket_sku_price |          |                |       |           |               |
| ---------------- | -------- | -------------- | ----- | --------- | ------------- |
| id               | sku_guid | rent_period_id | price | create_on | last_modified |

| pocket_rental_order |           |              |             |           |               |
| ------------------- | --------- | ------------ | ----------- | --------- | ------------- |
| id                  | client_id | signing_time | total_price | create_on | last_modified |

# Azure

- service: https://portal.azure.com/#blade/HubsExtension/BrowseResource/resourceType/Microsoft.Web%2Fsites
- DB: https://portal.azure.com/#blade/HubsExtension/BrowseResource/resourceType/Microsoft.Sql%2Fservers%2Fdatabases

![1621611197623](DevelopLog.assets/1621611197623.png)

# Swagger 
http://localhost:8080/swagger-ui.html