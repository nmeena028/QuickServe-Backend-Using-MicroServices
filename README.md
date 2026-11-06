# 🚀 QuickServe – QR-Based Restaurant Ordering System

QuickServe is a robust, production-ready microservices-based backend architecture designed for seamless restaurant table ordering, menu management, secure payment processing, and real-time order status synchronization.

---

## 🛠️ Tech Stack & Architecture
* **Language & Framework:** Java 21, Spring Boot (v4.1.2)
* **Microservices Architecture:**
  * **Service Discovery:** Netflix Eureka Server (`8761`)
  * **API Gateway:** Spring Cloud Gateway (`8080`)
  * **Restaurant Service:** (`8081`) – Menu & Counter Availability Management
  * **Order Service:** (`8082`) – Order Placement & Validation
  * **Payment Service:** (`8083`) – Secure Payment Processing & Transaction Management
* **Inter-Service Communication:** Spring Cloud OpenFeign
* **Database & Persistence:** MySQL, Spring Data JPA, Hibernate
* **Transaction & Safety:** `@Transactional`, Exception Handling

---

## 🛡️ Key Features & Critical Bug Fixes
1. **Duplicate Payment Prevention:** 
   * Implemented robust validation checks (`findByOrderId`) in the `PaymentService` to ensure a successful payment cannot be processed twice for the same order.
2. **Duplicate Order Prevention:** 
   * Added `existsByTableIdAndStatus` verification in the `OrderService` to prevent customers from placing multiple active `PENDING` orders for the same table simultaneously.
3. **Counter Dashboard Control (`isAvailable` Flag):** 
   * Integrated real-time item availability toggling managed via the restaurant counter dashboard. If an item is marked out-of-stock, the `OrderService` instantly rejects any new orders containing that item.
4. **Database Transaction Safety:** 
   * Applied `@Transactional` boundaries on critical order creation flows to guarantee complete data consistency and prevent partial or orphan records on failure.
5. **Real-Time Order Status Synchronization:** 
   * Integrated **Spring Cloud OpenFeign** (`OrderClient`) between Payment and Order services. Once a payment hits `SUCCESS`, the order status automatically updates from `PENDING` to `PAID` instantly.

---

## 📂 Project Structure
```text
QuickServe-Backend/
├── eureka-server/         # Service Registry (Port 8761)
├── api-gateway/           # API Gateway (Port 8080)
├── restaurant-service/    # Menu & Item Management (Port 8081)
├── order-service/         # Order Processing & Validation (Port 8082)
└── payment-service/       # Payment & Transaction Service (Port 8083)
🔗 API Endpoints Documentation
1. Restaurant Service (http://localhost:8081 or via Gateway http://localhost:8080/api/restaurant)
Get Menu: GET /api/restaurant/menu

Update Product Availability (Dashboard Toggle): PUT /api/restaurant/products/{id}/availability?available=true

2. Order Service (http://localhost:8082 or via Gateway http://localhost:8080/api/orders)
Place Order: POST /api/orders/place

Request Body Example:

JSON
{
  "customerName": "Nitin Meena",
  "phoneNumber": "9876543210",
  "tableId": 5,
  "items": [
    { "productId": 1, "quantity": 2 }
  ]
}
Get Order by ID: GET /api/orders/{id}

Update Order Status: PUT /api/orders/{id}/status?status=PAID

3. Payment Service (http://localhost:8083 or via Gateway http://localhost:8080/api/payments)
Process Payment: POST /api/payments/pay

Request Body Example:

JSON
{
  "orderId": 1,
  "amount": 450.0,
  "paymentStatus": "SUCCESS"
}
Get Payment by Order ID: GET /api/payments/order/{orderId}

⚙️ How to Run Locally
To run the system locally, start the microservices in the following strict order:

Eureka Server (8761)

Restaurant Service (8081)

Order Service (8082)

Payment Service (8083)

API Gateway (8080)
