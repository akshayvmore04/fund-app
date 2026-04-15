# 💰 Fund Management System (Backend)

## 📌 Overview

This system simulates a real-world **fund management platform** where users can manage group funds, track payments, issue loans, and monitor repayments with full transparency and security.
It focuses on **financial workflows, state transitions, and secure API design**, similar to systems used in fintech applications.

A secure, production-style backend system for managing funds, payments, loans, and EMI workflows.
Built using **Spring Boot**, **Spring Security**, and **MySQL**, following clean architecture and industry best practices.

---

## 🚀 Features

### 🔐 Authentication & Security

* JWT-based authentication (stateless)
* Role-Based Access Control (ADMIN, MEMBER)
* BCrypt password encryption
* Secure API endpoints using Spring Security

---

### 👥 User & Fund Management

* User registration & login
* Create and manage funds
* Add members to funds
* View fund details

---

### 💳 Payment System

* Payment request API
* Payment approval workflow (Admin only)
* Pending payments tracking
* Defaulter detection
* Payment history (audit-ready)

---

### 💰 Loan Management

* Issue loan with interest calculation
* EMI calculation and tracking
* EMI request & approval system
* Remaining loan balance tracking
* Automatic loan closure when fully paid

---

### 📊 Dashboard & Reporting

* Fund dashboard summary:

  * Total members
  * Expected collection
  * Total collected
  * Pending amount
* Clean, structured API responses for frontend integration

---

## 🏗 Architecture

The project follows a **layered architecture**:

```
Controller → Service → Repository → Database
```

### 📁 Project Structure

```
com.fundapp
│
├── controller      # REST APIs
├── service         # Business logic
├── repository      # Database access (JPA)
├── entity          # Database models
├── dto             # Request/Response models
├── security        # JWT & Spring Security
├── exception       # Global error handling
```

---

## 🔄 Request Flow

```
Client → JWT Filter → Spring Security → Controller → Service → Repository → DB
```

---

## 🧠 Technologies Used

* Java 17+
* Spring Boot
* Spring Security 6
* JWT (JSON Web Token)
* Spring Data JPA (Hibernate)
* MySQL
* Maven

---

## 🔐 API Security

* Public endpoints:

  * `/auth/**`

* Protected endpoints:

  * All other APIs require JWT token

* Role-based restrictions:

  * Loan issuing → ADMIN only
  * EMI approval → ADMIN only
  * Payment approval → ADMIN only

---

## 🧪 Testing

Tested using **Postman**:

### Authentication Flow:

1. Login → Receive JWT token
2. Add token in header:

```
Authorization: Bearer <token>
```

3. Access protected APIs

### Scenarios Verified:

* No token → `401 Unauthorized`
* Invalid token → `401 Unauthorized`
* Wrong role → `403 Forbidden`
* Valid access → `200 OK`

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the repository

```bash
git clone https://github.com/akshayvmore04/fund-app.git
cd fund-app
```

### 2️⃣ Configure Database

Create a MySQL database and update:

```
src/main/resources/application.properties
```

Example:

```
spring.datasource.url=jdbc:mysql://localhost:3306/fund_app
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

---

### 3️⃣ Run the application

```bash
mvn spring-boot:run
```

---

## 📈 Current Status

✔ Fully functional backend
✔ Secure REST APIs
✔ Role-based authorization
✔ Financial workflows implemented
✔ Production-style architecture

---

## 🎯 Future Improvements

* Email/SMS notifications
* Frontend (Flutter / React)
* Payment gateway integration
* Advanced reporting & analytics
* Docker deployment

---

## 👨‍💻 Author

**Akshay More**

* Backend Developer (Java & Spring Boot)
* Passionate about building real-world systems
* Focused on clean architecture & problem-solving

---

## ⭐ If you like this project
Give it a ⭐ on GitHub — it motivates me to build more!
