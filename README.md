# 🏦 Banking System

A console-based **Banking System** developed using **Java, JDBC, MySQL, and Maven**.
The project demonstrates how a real-world banking application can be structured using **Object-Oriented Programming, DAO architecture, service-layer design, exception handling, and database connectivity**.

This project is being developed incrementally as a practical Java backend project.

---

## 📌 Project Overview

The Banking System allows users to perform basic banking operations through a console-based interface.

The application communicates with a **MySQL database** using **JDBC** and follows a layered architecture to separate database operations, business logic, models, and configuration.

### Current Operations

* Create a new bank account
* Search for an account
* View all accounts
* Deposit money
* Withdraw money
* Check account balance
* Handle insufficient-balance situations
* Store account information in MySQL

---

## 🛠️ Technologies Used

* **Java** – Core application development
* **JDBC** – Database connectivity
* **MySQL** – Data storage
* **Maven** – Project and dependency management
* **Git** – Version control
* **GitHub** – Remote repository and project history

---

## 🏗️ Project Architecture

The project follows a layered structure:

```text
BankingSystem
│
├── .mvn/
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── virag/
│   │               └── banking/
│   │                   │
│   │                   ├── App.java
│   │                   │
│   │                   ├── config/
│   │                   │   └── DBConnection.java
│   │                   │
│   │                   ├── dao/
│   │                   │   └── AccountDAO.java
│   │                   │
│   │                   ├── exception/
│   │                   │   └── InsufficientBalanceException.java
│   │                   │
│   │                   ├── model/
│   │                   │   └── Account.java
│   │                   │
│   │                   └── service/
│   │                       └── AccountService.java
│   │
│   └── test/
│       └── java/
│
├── .gitignore
├── pom.xml
└── README.md
```

---

## 📂 Package Responsibilities

### `model`

Contains classes representing application data.

Example:

```text
Account.java
```

The `Account` model represents a bank account and contains information such as:

* Account number
* Account holder name
* Balance
* Phone number
* Email

### `dao`

The **Data Access Object (DAO)** layer handles communication with the database.

```text
AccountDAO.java
```

Responsibilities include operations such as:

* Creating accounts
* Searching accounts
* Retrieving accounts
* Updating account information
* Performing database-related operations

### `service`

The service layer contains the application's **business logic**.

```text
AccountService.java
```

It acts as an intermediate layer between the application and DAO.

```text
Application
     ↓
Service
     ↓
DAO
     ↓
MySQL Database
```

### `config`

Contains database configuration and connection-related functionality.

```text
DBConnection.java
```

This class is responsible for establishing the JDBC connection with MySQL.

### `exception`

Contains custom exceptions used by the application.

```text
InsufficientBalanceException.java
```

This exception is used when a withdrawal operation exceeds the available account balance.

---

## 🗄️ Database

The application uses **MySQL** as its database.

The account table contains fields such as:

| Column        | Description                   |
| ------------- | ----------------------------- |
| `account_no`  | Unique account number         |
| `holder_name` | Account holder's name         |
| `balance`     | Current account balance       |
| `phone`       | Account holder's phone number |
| `email`       | Account holder's email        |

The application connects to MySQL through **JDBC**.

---

## 🔄 Application Flow

A typical operation follows this flow:

```text
User
 │
 ▼
App.java
 │
 ▼
AccountService
 │
 ▼
AccountDAO
 │
 ▼
JDBC
 │
 ▼
MySQL
```

This separation makes the project easier to maintain and extend.

---

## 💰 Banking Operations

### Create Account

Creates a new bank account and stores the account details in the database.

### Search Account

Searches for an account using its account number.

### Deposit Money

Adds the specified amount to an account's current balance.

### Withdraw Money

Withdraws money after checking whether the account has sufficient balance.

If the requested amount is greater than the available balance, the application handles the situation using the custom `InsufficientBalanceException`.

### View All Accounts

Retrieves and displays account information stored in the database.

---

## ⚙️ Maven

The project uses **Apache Maven** for:

* Dependency management
* Project compilation
* Build management
* Running the application
* Managing the project lifecycle

Common commands:

```bash
mvn clean
```

```bash
mvn compile
```

```bash
mvn clean compile
```

```bash
mvn exec:java
```

---

## 🚀 How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/viragdesai830-sketch/BankingSystem.git
```

### 2. Navigate to the project

```bash
cd BankingSystem
```

### 3. Configure MySQL

Create the required database and account table in MySQL.

Update the database connection configuration in:

```text
src/main/java/com/virag/banking/config/DBConnection.java
```

> Do not commit database passwords or other sensitive credentials to GitHub.

### 4. Build the project

```bash
mvn clean compile
```

### 5. Run the application

```bash
mvn exec:java
```

---

## 🔐 Security Note

Database credentials should **not** be hardcoded in production applications.

For future development, the project can be improved by moving sensitive configuration such as:

* Database username
* Database password
* Database URL

to environment variables or an external configuration file.

---

## 📈 Future Improvements

The project is still under development. Planned improvements may include:

* Money transfer between accounts
* Transaction history
* User authentication
* PIN/password authentication
* Improved input validation
* Better exception handling
* Transaction management using JDBC
* Role-based access
* REST API using Spring Boot
* Improved database security
* Unit and integration testing
* Logging
* GUI or web interface

---

## 🎯 Learning Objectives

This project is being developed to gain practical experience with:

* Java
* Object-Oriented Programming
* JDBC
* MySQL
* Maven
* DAO architecture
* Service-layer architecture
* Exception handling
* CRUD operations
* Database transactions
* Git and GitHub
* Software project structure

---

## 📌 Project Status

**Status:** 🚧 In Development

The project is being developed incrementally, with new banking features and architectural improvements being added over time.

---

## 👨‍💻 Author

**Virag Desai**

GitHub: **viragdesai830-sketch**

---

## ⭐ Acknowledgement

This project is created as a practical learning project to understand Java backend development, database connectivity, software architecture, and version control using Git and GitHub.
