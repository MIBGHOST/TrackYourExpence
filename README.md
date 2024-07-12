ExpenseTracker

ExpenseTracker is an application designed to manage and track expenses. This project is bootstrapped with Spring Boot and utilizes a microservice architecture. It includes a user authentication service implemented using Spring Security with JWT (JSON Web Token) and Spring Data MongoDB. The application provides APIs for user registration, login, token verification, and CRUD operations for users.

Table of Contents

    Getting Started
    Prerequisites
    Installation
    Usage
    APIs
    Built With
    Authors
    License

Getting Started

These instructions will help you set up and run the ExpenseTracker application on your local machine for development and testing purposes.
Prerequisites

Before you begin, ensure you have the following installed on your machine:

    Java Development Kit (JDK) 8 or higher
    Maven 3.6.3 or higher
    MongoDB Atlas (or a local MongoDB instance)
    Git

Installation

Clone the repository: 
        
    git clone https://github.com/MIBGHOST/ExpenseTracker.git

sh 
        
    cd ExpenseTracker

Configure MongoDB:
            
    Update the application.properties file in src/main/resources with your MongoDB Atlas credentials or local MongoDB instance details.

properties

    spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/<dbname>?retryWrites=true&w=majority

Build the project:

sh
    
    mvn clean install
    
Run the application:
    
sh

    mvn spring-boot:run

Usage

Once the application is running, you can access the various APIs using tools like Postman or curl.
APIs

The application provides the following APIs:
    
    User Authentication Service
    Login User
    Verify Token
    User CRUD Operations
    Delete User

Built With

    Spring Boot - The framework used for application setup and configuration.
    Spring Security - Used for securing the application and implementing JWT-based authentication.
    Spring Data MongoDB - Used for MongoDB integration.
    MongoDB Atlas - Cloud-based MongoDB service.

Authors

    MIBGHOST

License

This project is licensed under the MIT License - see the LICENSE file for details.
