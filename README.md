# ⚽ JerseyVerse Backend

Backend REST API for **JerseyVerse**, a full-stack Football Jersey E-Commerce application built with **Java** and **Spring Boot**.

This project provides a secure, scalable, and modular backend that powers the JerseyVerse platform. It includes authentication and authorization using JWT, complete product management, shopping cart functionality, order processing, coupon management, invoice generation, customer management, and administrative features through a well-structured layered architecture.

The backend follows modern Spring Boot development practices with clean separation of concerns using Controller → Service → Repository architecture, making the project maintainable, extensible, and suitable as a real-world portfolio application.

> **Status:** Completed
> Backend Development • Frontend Integration • Module Testing • End-to-End Regression Testing

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

### API Documentation
- Swagger / OpenAPI

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

```text
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

> **API Documentation:** Complete API specifications and request/response examples are available through the integrated **Swagger/OpenAPI** interface after running the application.
