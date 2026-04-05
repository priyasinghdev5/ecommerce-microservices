Microservices Advanced Assignment – Deliverables
1. Introduction
This project implements a backend system for an eCommerce application using Microservices Architecture. The system is designed to be scalable, loosely coupled, and easily extendable for future enhancements.
________________________________________
2. Microservices Identified & Justification
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




3. Architecture Overview
High-Level Flow
1.	Client → API Gateway
2.	Gateway routes request to respective service
3.	Services communicate via REST (sync) or Kafka (async)
4.	Events published to Kafka → Notification Service consumes
________________________________________
4. Design Patterns Used
1. API Gateway Pattern
Centralized routing via Spring Cloud Gateway.
2. Service Discovery Pattern
Using Eureka for dynamic service registration.
3. Database per Service Pattern (Conceptual)
Each service owns its data (mongo DB for assignment).
4. Saga Pattern (Conceptual)
Used in order processing across services.
5. Event-Driven Architecture
Kafka used for async communication.
6. Circuit Breaker Pattern
(via Resilience4j)
________________________________________
5. Communication Patterns
Synchronous Communication
•	REST APIs using Spring Boot
•	Example:
o	Order Service → Inventory Service
o	Product Service → Product Detail Service
Asynchronous Communication
•	Kafka messaging
•	Example:
o	Order placed → Notification Service consumes event
________________________________________
6. APIs Implemented
Product Service
•	POST /products
•	GET /products
•	DELETE /products/{id}
Product Detail Service
•	POST /product-details
•	GET /product-details/{productId}
Cart Service
•	POST /cart/add
•	GET /cart/{userId}
•	DELETE /cart/remove
Order Service
•	POST /order/place
Payment Service
•	POST /payment/process
Auth Service
•	POST /auth/login
•	POST /auth/register
________________________________________






7. Cross-Cutting Concerns
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
________________________________________
8. CI/CD Pipeline (Conceptual)
1.	Code Commit → Git
2.	Build → Maven/Gradle
3.	Docker Image Creation
4.	Push to Registry
5.	Deploy via Docker Compose / Kubernetes
________________________________________
9. Docker & Containerization
•	Each service has its own Dockerfile
•	Docker Compose used to run:
o	Kafka
o	Zookeeper
o	All microservices
________________________________________




10. Assumptions
•	In-memory data storage used
•	Single user scenario for simplicity
•	Payment is simulated

________________________________________
11. Screenshots
•	Service startup logs
•	API responses
•	Kafka event logs
________________________________________

14. Future Enhancements
•	Add UI (React)
•	Implement Kubernetes deployment
•	Add caching (Redis)
•	Add monitoring (Prometheus + Grafana)
________________________________________
15. Conclusion
This system demonstrates a production-ready microservices architecture with proper separation of concerns, scalability, and extensibility.

