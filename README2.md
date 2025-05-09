# Spring Boot Application

This project is a Spring Boot application built using Java 21 and Spring Boot 3.4.5, managed with Gradle. The application includes two controllers: a public controller and a secure controller.

## Project Structure

```
spring-boot-app
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── springbootapp
│   │   │               ├── SpringBootAppApplication.java
│   │   │               ├── config
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── controllers
│   │   │               │   ├── PublicController.java
│   │   │               │   └── SecureController.java
│   │   │               └── filters
│   │   │                   └── AuthorizationFilter.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── static
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── springbootapp
│                       └── SpringBootAppApplicationTests.java
├── build.gradle
└── README.md
```

## Features

- **Public API**: Accessible without authentication at the endpoint `/api/public`.
- **Secure API**: Requires a valid bearer token in the authorization header to access the endpoint `/api/secure`.

## Setup Instructions

1. Clone the repository.
2. Navigate to the project directory.
3. Build the project using Gradle:
   ```
   ./gradlew build
   ```
4. Run the application:
   ```
   ./gradlew bootRun
   ```
5. Access the public endpoint at `http://localhost:8080/api/public`.
6. For the secure endpoint, ensure you provide a valid bearer token in the authorization header.

## Usage

- Use the public endpoint to retrieve public data.
- Use the secure endpoint to retrieve secure data, ensuring you include the authorization header with a bearer token.

## License

This project is licensed under the MIT License.