# Microservices Lab - Event-Driven Architecture with Kafka

This project contains:

- `order-service` on port `8081`
- `inventory-service` on port `8082`
- `billing-service` on port `8083`
- `api-gateway` on port `8080`
- Kafka in KRaft mode through `docker-compose.yml`

## Flow

1. Send `POST /orders` to the API Gateway.
2. Gateway forwards the request to `order-service`.
3. `order-service` publishes an `OrderCreatedEvent` to Kafka topic `order-topic`.
4. `inventory-service` consumes the event and updates stock.
5. `billing-service` consumes the event and generates an invoice.

## Run Kafka

```bash
docker compose up -d
```

## Build the project

```bash
mvn clean package
```

## Start the applications

Open four terminals in the project root and run:

```bash
mvn -pl order-service spring-boot:run
```

```bash
mvn -pl inventory-service spring-boot:run
```

```bash
mvn -pl billing-service spring-boot:run
```

```bash
mvn -pl api-gateway spring-boot:run
```

## Test with Postman

Request:

```http
POST http://localhost:8080/orders
Content-Type: application/json

{
  "orderId": "ORD-1001",
  "item": "Laptop",
  "quantity": 1
}
```

Expected response:

```json
{
  "message": "Order Created & Event Published",
  "order": {
    "orderId": "ORD-1001",
    "item": "Laptop",
    "quantity": 1
  }
}
```

Useful verification endpoints:

- `GET http://localhost:8081/orders`
- `GET http://localhost:8082/inventory`
- `GET http://localhost:8083/billing/invoices`

## Deliverables support

- `docker-compose.yml` is included.
- Service logs will show inventory and billing consumers processing each order event.
- Capture screenshots after running the four apps and Kafka container.
