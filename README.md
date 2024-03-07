# AWSGroup Web service
This is a Maven Spring Boot application that implements a Continuous Integration/Continuous Deployment (CI/CD) pipeline on AWS. The project will be hosted in AWS and will be accessible via a URL for a short period of time. The project also includes a build process on GitHub Actions for testing.

The project aims to create a backend server that can be used by companies to manage their departments in different cities as well as the employees working for the company.
The application provides a RESTful API for client applications to interact with.

The project uses Spring Security to authenticate users and OAuth2 to authorize them.
The application will automatically create an admin account if it does not already exist. The username is *admin* and the password is *password*.

The application is connected to a RDS MySQL database and uses Spring Data JPA to access the data.
If the RDS MySQL database is not available, please create a MySQL database locally and update the application.properties file with the correct database URL, username, and password.

Interaction with the application can be done by using a client application created by our fellow co-students or by using Postman.
Please clone the client application from the following repository: ********


## Goal

The goal of this project is to automate the build, test, and deployment process to streamline the development flow.
This project is also about practicing the use of cloud services.
Besides learning new technologies and services, the project also aims to practices collaboration with another development team to create a client for each other's web service.

## API Documentation

To learn more about the Restful API and the endpoints, please run the application and navigate to the following URL: http://localhost:5000/swagger-ui/index.html#/

## Technologies and Tools

- Java
- Spring Boot
- Maven
- AWS
- GitHub Actions
- MySQL

## Features

- User Authentication: The application uses Spring Security to authenticate users. An admin account is automatically created if it does not already exist.
- User Authorization: The application uses OAuth2 to authorize users, ensuring secure access to resources.
- Database Connectivity: The application is connected to a RDS MySQL database and uses Spring Data JPA for data access.
- CI/CD Pipeline: The project implements a CI/CD pipeline on AWS, automating the build, test, and deployment process.
- RESTful API: The application provides a RESTful API for client applications to interact with.

## Dependencies:

- Spring Web
- Spring Security
- Spring Data JPA
- MySQL Driver
- Lombok
- OAuth2 Client
- JUnit

## How to Run the Project

To run this project locally, follow these steps:

1. Clone this repo to your local machine
2. Open the project in your favorite IDE (e.g., IntelliJ IDEA)
3. Run `mvn clean install` to build the project
4. Run `mvn spring-boot:run` to start the application

Note that you need to have Java and Maven installed on your machine to be able to run the project.

## Credits

### Collaborators in this project:
- **[Clara Brorson](https://github.com/clarabrorson)**
- **[Jafar Hussein](https://github.com/Jafar-Hussein)**
- **[Fredrik Rinstad](https://github.com/fringston)**

### To learn more about our CI/CD pipeline, please refer to [CI/CD Process](CI_CD_PROCESS.md).

## LICENSE
