# ⚽ JerseyVerse Backend

Backend REST API for **JerseyVerse**, a full-stack Football Jersey E-Commerce application built with **Java** and **Spring Boot**.

This project provides a secure, scalable, and modular backend that powers the JerseyVerse platform. It includes authentication and authorization using JWT, complete product management, shopping cart functionality, order processing, coupon management, invoice generation, customer management, and administrative features through a well-structured layered architecture.

The backend follows modern Spring Boot development practices with clean separation of concerns using Controller → Service → Repository architecture, making the project maintainable, extensible, and suitable as a real-world portfolio application.

> **Status:** Completed
> Backend Development • Frontend Integration • Module Testing • End-to-End Regression Testing

## 🚀 Deployment

The JerseyVerse backend is deployed as a production Spring Boot application using the following infrastructure:

| Component             | Platform        |
| --------------------- | --------------- |
| Backend Hosting       | Render          |
| Containerization      | Docker          |
| Database              | Neon PostgreSQL |
| Product Image Storage | Cloudinary      |

The Spring Boot backend is containerized using **Docker** and deployed on **Render**.

The application connects to a production **PostgreSQL database hosted on Neon** and uses **Cloudinary** for persistent product image storage in the production environment.

### Production API

**🔗 [JerseyVerse Backend API](https://jerseyverse-backend-java.onrender.com)**

The backend provides the REST APIs consumed by the deployed JerseyVerse React frontend.


## ✨ Features

### 🔐 Authentication & Authorization
- User registration and login
- JWT-based authentication
- Role-based access control (Admin & Customer)
- Secure password encryption using BCrypt
- Protected REST APIs with Spring Security

### 👤 User & Address Management
- User profile management
- Password change functionality
- Address management
- Default shipping address support

### 🛍️ Product Catalog
- Product listing with pagination
- Product search and filtering
- Product sorting
- Product details with variants (size & stock)
- Category-based organization
- Latest products endpoint

### 🛒 Shopping Cart
- Add products to cart
- Update item quantity
- Remove items from cart
- Clear cart
- Checkout validation
- Real-time price calculation

### 🎟️ Coupon Management
- Apply coupon codes
- Remove applied coupons
- Discount calculation
- Coupon validation
- Coupon usage tracking

### 📦 Order Management
- Place orders
- Order history
- Order details
- Order status tracking
- Order address snapshot
- Automatic stock deduction

### 🧾 Invoice Generation
- PDF invoice generation
- Downloadable invoices
- Order summary documentation

### 🛠️ Admin Features
- Dashboard analytics
- Product management
- Customer management
- Coupon management
- Order management
- Product image upload

### 🔒 Security
- JWT authentication
- Request authorization
- Bean Validation
- Global exception handling
- Input validation
- Secure API design

### ⚙️ Backend Architecture
- Layered architecture
- DTO-based API design
- Repository pattern
- Entity-DTO mapping
- JPA Specifications for filtering
- Centralized exception handling

## 🛠️ Tech Stack

### Backend
- Java 25
- Spring Boot 4.1.0
- Spring MVC
- Spring Security
- Spring Data JPA (Hibernate)

### Authentication & Security
- JWT (JSON Web Token)
- BCrypt Password Encoder
- Role-Based Access Control (RBAC)

### Database
- PostgreSQL

### Build & Dependency Management
- Maven

### File Storage
- Local File Storage (Multipart Image Upload)

### Development Tools
- IntelliJ IDEA
- Postman
- Git & GitHub

### Testing
- Manual API Testing
- End-to-End Integration Testing

## 🏗️ Architecture Overview

The JerseyVerse Backend follows a **Layered Architecture** to ensure clear separation of concerns, maintainability, and scalability. Each layer has a well-defined responsibility, making the application easier to understand, test, and extend.

### Layered Architecture

```
                HTTP Request
                      │
                      ▼
              Spring Security
          (JWT Authentication)
                      │
                      ▼
               Controller Layer
                      │
                      ▼
          Service Interface Layer
                      │
                      ▼
      Service Implementation Layer
                      │
                      ▼
             Repository Layer
                      │
                      ▼
                PostgreSQL Database
```

### Request Lifecycle

1. The client sends an HTTP request to the backend.
2. Spring Security intercepts the request and validates the JWT token (if required).
3. The appropriate controller receives the request.
4. The controller delegates business logic to the service layer.
5. The service interacts with the repository layer for data access.
6. The repository communicates with the PostgreSQL database using Spring Data JPA (Hibernate).
7. The response is mapped to DTOs and returned to the client.

### Design Principles

- Layered architecture
- Separation of concerns
- DTO-based request and response models
- Repository pattern using Spring Data JPA
- Entity-to-DTO mapping
- Centralized exception handling
- Bean Validation for request validation
- Role-based API authorization

## 📁 Project Structure

The backend follows a layered architecture with a clear separation of responsibilities. Each package has a dedicated purpose, making the codebase easier to maintain, extend, and test.

```text
src
└── main
    ├── java
    │   └── com.ecommerce.jerseyverse
    │       ├── config
    │       ├── controller
    │       │   ├── admin
    │       │   └── customer
    │       ├── dto
    │       │   ├── request
    │       │   └── response
    │       ├── entity
    │       ├── enums
    │       ├── exception
    │       ├── mapper
    │       ├── repository
    │       ├── security
    │       ├── service
    │       │   ├── admin
    │       │   ├── customer
    │       │   └── impl
    │       ├── specification
    │       ├── util
    │       └── JerseyverseBackendApplication.java
    │
    └── resources
        ├── application.properties
        └── static
```

### Package Responsibilities

| Package | Responsibility |
|----------|----------------|
| **config** | Spring Boot and application configuration classes. |
| **controller** | REST API endpoints for customer and admin operations. |
| **dto** | Request and response models used for API communication. |
| **entity** | JPA entity classes mapped to database tables. |
| **enums** | Application-wide enumerations such as user roles and order status. |
| **exception** | Custom exceptions and centralized exception handling. |
| **mapper** | Converts entities to DTOs and vice versa. |
| **repository** | Spring Data JPA repositories for database access. |
| **security** | JWT authentication, authorization, filters, and Spring Security configuration. |
| **service** | Business logic interfaces and implementations. |
| **specification** | Dynamic filtering using JPA Specifications. |
| **util** | Shared utility classes used across the application. |
| **resources** | Application configuration and static resources. |

## 📡 API Modules

The backend exposes RESTful APIs organized into functional modules. The following table provides an overview of the available API groups.

| Module | Description |
|---------|-------------|
| **Authentication** | User registration, login, JWT authentication, and password management. |
| **User Management** | Retrieve and update user profile information. |
| **Address Management** | Manage customer shipping addresses and default address selection. |
| **Product Catalog** | Browse products, view product details, search, filter, sort, and pagination. |
| **Shopping Cart** | Add products to cart, update quantities, remove items, clear cart, and validate checkout. |
| **Coupon Management** | Apply and remove coupons, validate eligibility, and calculate discounts. |
| **Order Management** | Place orders, retrieve order history, view order details, and track order status. |
| **Invoice Management** | Generate and download PDF invoices for completed orders. |
| **Admin Dashboard** | View business statistics and dashboard analytics. |
| **Admin Product Management** | Create, update, delete, and manage products, variants, stock, and product images. |
| **Admin Order Management** | View, manage, and update customer orders. |
| **Admin Customer Management** | Browse customer information and order history. |
| **Admin Coupon Management** | Create, update, activate, deactivate, and manage coupons. |

## 🔒 Security & Validation

Security is a core part of the JerseyVerse Backend. The application follows modern Spring Boot security practices to protect REST APIs and ensure secure access to resources.

### Authentication
- JWT (JSON Web Token) based authentication
- Stateless session management
- Secure user login and registration
- Password encryption using BCrypt

### Authorization
- Role-Based Access Control (RBAC)
- Separate access levels for **ADMIN** and **CUSTOMER**
- Protected administrative endpoints
- Authenticated access to customer-specific resources

### Request Validation
- Bean Validation for request payloads
- Input validation before business logic execution
- Consistent validation error responses

### Exception Handling
- Centralized global exception handling
- Standardized API error responses
- Meaningful HTTP status codes for client and server errors

### Data Protection
- Passwords stored in encrypted form
- Database access through Spring Data JPA
- DTO-based API communication to avoid exposing internal entities

## ⚙️ Configuration

The application is configured using the `application.yaml` file located at:

```text
src/main/resources/application.yaml
```

### Database Configuration

Configure your PostgreSQL database connection:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jerseyverse_db
    username: YOUR_DATABASE_USERNAME
    password: YOUR_DATABASE_PASSWORD
```

### JPA & Hibernate

The project uses Spring Data JPA with Hibernate for ORM.

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

> **Note:** The `update` strategy is suitable for local development. For production environments, consider using a database migration tool such as Flyway or Liquibase.

> **Note:** Before running the application, update the placeholder values in `application.yaml` with your local PostgreSQL credentials and a secure JWT secret.

### Multipart File Upload

The backend supports multipart image uploads for product management.

```yaml
spring:
  servlet:
    multipart:
      max-file-size: 5MB
      max-request-size: 6MB
```

### JWT Configuration

JWT is used for stateless authentication.

```yaml
jwt:
  secret: YOUR_JWT_SECRET
  expiration: 86400000
```

### Product Image Storage

Uploaded product images are stored locally.

```yaml
app:
  upload:
    product-dir: uploads/products
```

### Server Configuration

```yaml
server:
  port: 8081
```

## 🔗 Related Repository

This project is part of the **JerseyVerse** full-stack application.

| Repository | Description |
|------------|-------------|
| **JerseyVerse Frontend** | React.js frontend application for customer and admin interfaces. |

> **Frontend Repository:** https://github.com/jainishpatel25/jerseyverse-frontend

## 🚀 Future Improvements

The following enhancements are planned for future versions of the project:

- Integrate Swagger / OpenAPI documentation
- Dockerize the application
- Implement CI/CD pipelines using GitHub Actions
- Add unit and integration testing
- Introduce Flyway or Liquibase for database migrations
- Support cloud-based file storage (AWS S3, Cloudinary, etc.)
- Add email notifications for order updates
- Implement payment gateway integration (Stripe/Razorpay)
- Add monitoring and logging dashboards
- Deploy the application to a cloud platform

## 📄 License

This project is licensed under the MIT License.

See the `LICENSE` file for more information.

## 👨‍💻 Author

**Jainish Patel**

- Java Backend Developer
- Spring Boot Enthusiast
- Passionate about building scalable backend applications

If you found this project useful, feel free to ⭐ the repository.




