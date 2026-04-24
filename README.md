                        

**1. Introduction**
   This project implements a backend system for an eCommerce application using Microservices Architecture. The system is designed to be scalable, loosely coupled, and easily extendable for future enhancements.
________________________________________

**2. Microservices Identified & Justification**


1. API Gateway (api-gateway)
   Purpose: Single entry point for all client requests.
   Why:
   •	Centralized routing
   •	Security enforcement
   •	Rate limiting and filtering

2. Auth Service (auth-service)
   Purpose: Handles authentication & authorization.
   Why:
   •	Separation of concerns
   •	Supports JWT-based authentication

3. Product Service (product-service)
   Purpose: Manages product catalogue.
   Responsibilities:
   •	Add/remove products
   •	Fetch product list

4. Product Detail Service (product-detail-service)
   Purpose: Stores product-specific details like size, design.
   Why:
   •	Follows Single Responsibility Principle
   •	Enables independent scaling

5. Inventory Service (inventory-service)
   Purpose: Tracks stock availability.
   Why:
   •	Needed for order validation

6. Cart Service (cart-service)
   Purpose: Handles cart operations.
   Responsibilities:
   •	Add/remove items
   •	Maintain user cart

7. Order Service (order-service)
   Purpose: Handles order placement.
   Responsibilities:
   •	Create order
   •	Coordinate with payment & inventory

8. Payment Service (payment-service)
   Purpose: Processes payments.

9. Notification Service (notification-service)
   Purpose: Sends notifications (console logging).
   Why:
   •	Event-driven design demonstration

10. Config Server (config-server)
    Purpose: Centralized configuration management.

11. Eureka Server (eureka-server)
    Purpose: Service discovery.

________________________________________


**3. Architecture Overview**

High-Level Flow
1.	Client → API Gateway
2.	Gateway routes request to respective service
3.	Services communicate via Web Client (sync) or Kafka (async)
4.	Events published to Kafka → Notification Service consumes
________________________________________

**4. Design Patterns Used**

1. API Gateway Pattern
   Centralized routing via Spring Cloud Gateway.

2. Service Discovery Pattern
   Using Eureka for dynamic service registration.

3. Database per Service Pattern (Conceptual)
   Each service owns its data (mongo DB used for assignment).

4. Saga Pattern (Conceptual)
   A sequence of local transactions, where each service:
   •	Updates its own DB
   •	Publishes an event
   •	Next service continues
   •	If something fails → compensating transactions rollback previous steps
   Order Service → creates order
   Payment Service → deducts money
   Inventory Service → reserves stock
   ❌ If inventory fails →
   ✔ Payment is refunded
   ✔ Order is cancelled


5. Event-Driven Architecture
   Kafka used for async communication.

6. Circuit Breaker Pattern
   (via Resilience4j)

7. CQRS (Command Query Responsibility Segregation) (Conceptual)
   •	Write operations (Commands)
   •	Read operations (Queries)
   •	Read and write workloads are different
   •	Reads are heavy → optimize separately
   🔹 Example
   Write Model:
   POST /orders → writes to DB
   Read Model:
   GET /orders → fetch from optimized DB (ElasticSearch)
   🔹 Architecture
   •	Command DB → normalized
   •	Query DB → denormalized (fast reads)

8.) Bulkhead Pattern (Conceptual)
Isolation pattern → prevents failure from spreading
🔹 Example
•	Separate thread pools:
o	Payment service
o	Inventory service
o	If payment fails → inventory still works
🔹 In Java it can be achieved using Resilience4j framework
@Bulkhead(name = "paymentService")
“Bulkhead isolates resources like thread pools so failure in one service doesn’t impact others.”
________________________________________

**5.	Use of Resilience4j**

Resilience4j is a lightweight, Java-based fault tolerance library designed to help applications become more resilient by handling failures gracefully. It provides tools like circuit breakers, retries, rate limiters, and bulkheads, allowing developers to build robust systems that can withstand network issues, service outages, or high traffic.
🔑 Core Features of Resilience4j
•	Circuit Breaker: Prevents cascading failures by stopping calls to a failing service until it recovers.
•	Retry: Automatically retries failed operations with configurable limits and delays.
•	Rate Limiter: Controls the rate of requests to avoid overwhelming services.
•	Bulkhead: Isolates resources so that failures in one part of the system don’t affect others.
•	Time Limiter: Sets maximum execution times for operations to avoid indefinite waits.
•	Cache: Provides caching decorators to reduce repeated calls to expensive operations.
________________________________________

**6. Communication Patterns**
   Synchronous Communication
   •	Synchronous communication implemented using Webclient.
   •	Example:
   o	Product Service → Inventory Service
   o	Product Service → Product Detail Service
   Asynchronous Communication
   •	Kafka messaging
   •	Example:
   o	Order placed → Notification Service consumes event
________________________________________

**7. APIs Implemented**
   Product Service
   •	POST /products
   •	GET /products
   •	DELETE /products/{id}
   Product Detail Service
   •	POST /product-details
   •	GET /product-details/{productId}
   Cart Service
   •	POST /cart/{userId}/add
   •	GET /cart/{userId}
   •	DELETE /cart/{userId}/remove/{productId}
   Order Service
   Checkout	: /orders/checkout/{userId}
   Get all orders of user	: /orders/user/{userId}
   Get single order	: /orders/{orderId}
   Update status	: /orders/{orderId}/status
   Cancel order	: /orders/{orderId}
   Payment Service
   •	POST /payment
   Auth Service
   •	POST /auth/login
   •	POST /auth/register
________________________________________

**8. Cross-Cutting Concerns**
   Logging & Tracing
   •	Implemented using Spring Boot logging
   •	Distributed tracing via Zipkin (optional extension)
   Exception Handling
   •	Global Exception Handler (@ControllerAdvice)
   Security
   •	JWT-based authentication
   •	API Gateway validates tokens
   Scalability
   •	Horizontal scaling via Docker containers
   •	Stateless services
   •	Kafka for decoupling
   Caching
   •	REDIS used for caching (Product details saved in redis cache)
________________________________________

**9. CI/CD Pipeline (Conceptual)**
1.	Code Commit → Git
2.	Build → Maven/Gradle
3.	Docker Image Creation
4.	Push to Registry
5.	Deploy via Docker Compose / Kubernetes
________________________________________
**10. Docker & Containerization**
    •	Each service has its own Dockerfile
    •	Docker Compose used to run:
    o	Kafka
    o	Zookeeper
    o	All microservices
________________________________________

**11. Assumptions**
    •	Mongo DB in docker container used as a data storage.
    •	Single user scenario for simplicity.
    •	Payment is simulated.

________________________________________

**12. Future Enhancements**
    •	Add UI (React)
    •	Implement Kubernetes deployment
    •	Add centralized logging using Splunk or ELK stack
    •	Add monitoring (Prometheus + Grafana)
    •	Add idempotency keys
    •	Inventory validation and reservation on placing order
________________________________________

**13. Conclusion**
    This system demonstrates a production-ready microservices architecture with proper separation of concerns, scalability, and extensibility.

