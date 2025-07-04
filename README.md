# Banking App

A simple web-based banking application built with Spring Boot, Thymeleaf, and MySQL. This app allows users to manage bank accounts, including creating, editing, searching, depositing, and withdrawing funds.

## Collaborators
- Nabilah Nuril Zahra M. Karim (nabilahnzmkarim15@gmail.com)
- Nadia Fuspa Dewi (nadiapusfa@gmail.com)

## Features

- Create, view, edit, and delete bank accounts
- Deposit and withdraw funds
- Search accounts by name, account number, email, or phone
- Multi-currency support (IDR, USD, EUR)
- Responsive UI with Bootstrap
- Currency formatting for balances

## Technologies Used

- Java 24
- Spring Boot 3.5.3
- Spring Data JPA
- Thymeleaf
- MySQL
- Lombok
- Bootstrap 5

## Getting Started

### Prerequisites

- Java 17+ (Java 24 recommended)
- Maven
- MySQL

### Setup

1. **Clone the repository**

   ```sh
   git clone https://github.com/Sw4nhilde/banking-app
   cd banking-app
   ```

2. **Configure the database**

   Edit [`src/main/resources/application.properties`](src/main/resources/application.properties) with your MySQL credentials:

   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/banking_system
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```

   Create the database if it doesn't exist:

   ```sql
   CREATE DATABASE banking_system;
   ```

3. **Build and run the application**

   ```sh
   ./mvnw spring-boot:run
   ```

   Or on Windows:

   ```sh
   mvnw.cmd spring-boot:run
   ```

4. **Access the app**

   Open [http://localhost:8080/accounts](http://localhost:8080/accounts) in your browser.

## Project Structure

- `src/main/java/com/example/banking_app/` - Java source code
- `src/main/resources/templates/accounts/` - Thymeleaf HTML templates
- `src/main/resources/application.properties` - Application configuration
- `pom.xml` - Maven build file

## Main Files

- [`BankingAppApplication.java`](src/main/java/com/example/banking_app/BankingAppApplication.java): Main Spring Boot application class
- [`AccountController.java`](src/main/java/com/example/banking_app/controller/AccountController.java): Handles web requests for accounts
- [`AccountService.java`](src/main/java/com/example/banking_app/service/AccountService.java): Business logic for accounts
- [`AccountRepository.java`](src/main/java/com/example/banking_app/repository/AccountRepository.java): JPA repository for accounts
- [`Account.java`](src/main/java/com/example/banking_app/model/Account.java): Account entity

## License

This project is for educational purposes.
