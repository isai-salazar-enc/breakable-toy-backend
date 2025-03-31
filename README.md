# Breakable Toy API

A backend API built with **Spring Boot** to manage products and categories. This project is designed as a learning tool to demonstrate the use of Spring Boot, in-memory repositories, and RESTful APIs.

---

## **Table of Contents**
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
5. [Setup and Installation](#setup-and-installation)
6. [Running the Application](#running-the-application)
7. [API Endpoints](#api-endpoints)
8. [Testing](#testing)
9. [Versioning](#versioning)
10. [Future Improvements](#future-improvements)

---

## **Project Overview**
The Breakable Toy API is a RESTful API that allows users to manage products and categories. It uses in-memory repositories to simulate database operations, making it lightweight and easy to run without additional dependencies.

---

## **Features**
- **Product Management**:
  - Create, update, delete, and retrieve products.
  - Manage product stock (mark as in-stock or out-of-stock).
  - Retrieve paginated lists of products.
  - Calculate inventory metrics (e.g., total value, average price).

- **Category Management**:
  - Retrieve all categories.

- **In-Memory Repositories**:
  - Simulates database operations using in-memory storage.

---

## **Technologies Used**
- **Spring Boot**: Version 3.4.0
- **Java**: Version 17
- **Maven**: Dependency and build management
- **Testing**: JUnit 5 and Mockito for unit testing


## **Project Structure**
The project follows a standard **Spring Boot** structure:

src/main/java/com/encora/breakable_toy_API/ 
├── config/ # Configuration files
├── controller/ # REST controllers for handling API requests 
├── models/ # Data models and DTOs 
├── repository/ # In-memory repositories for data storage 
├── service/ # Business logic and service layer 
└── BreakableToyApiApplication.java # Main application entry point

src/test/java/com/encora/breakable_toy_API/ 
├── service/ # Unit tests for services 
└── BreakableToyApiApplicationTests.java # Application context test

---

## **Setup and Installation**

### **Prerequisites**
1. **Java 17**: Ensure you have JDK 17 installed.
2. **Maven**: Install Maven for dependency management.
3. **Git**: Clone the repository using Git.

### **Clone the Repository**
Clone the project to your local machine using Git:
```bash
git clone https://github.com/your-username/breakable_toy_API.git
cd breakable_toy_API
```

### **Install Dependencies**
Run the following command to download and install all required dependencies:
```bash
mvn clean install
```

---

## **Running the Application**

### **Run Locally**
To start the application, use the following Maven command:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

### **Configuration**
The application uses the following default configurations:
- **Port**: `8080`
- **CORS**: Configured to allow requests from `http://localhost:[*]` (defined in `WebConfig.java`).

You can modify these settings in the `application.properties` file located in `src/main/resources`.

---

## **API Endpoints**

### **Product Endpoints**
| HTTP Method | Endpoint                     | Description                              |
|-------------|------------------------------|------------------------------------------|
| `GET`       | `/api/products`              | Retrieve all products                   |
| `GET`       | `/api/products/{page}`       | Retrieve paginated products             |
| `POST`      | `/api/products`              | Create a new product                    |
| `PUT`       | `/api/products/{id}/instock` | Update stock for a product              |
| `POST`      | `/api/products/{id}/outofstock` | Mark a product as out of stock          |
| `PUT`       | `/api/products/{id}`         | Update an existing product              |
| `DELETE`    | `/api/products/{id}`         | Delete a product                        |
| `GET`       | `/api/metrics`               | Retrieve inventory metrics              |

### **Category Endpoints**
| HTTP Method | Endpoint                     | Description                              |
|-------------|------------------------------|------------------------------------------|
| `GET`       | `/api/categories`            | Retrieve all categories                 |

---

## **Testing**

### **Run Tests**
To execute all unit tests, use the following command:
```bash
mvn test
```

### **Test Coverage**
The project includes unit tests for:
- **Service Layer**:
  - `ProductServiceTest`: Covers product-related business logic.
  - `CategoryServiceTest`: Covers category-related business logic.
- **Global Exception Handling**:
  - Ensures proper error responses for invalid inputs.