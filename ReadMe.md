# Customer Invoice API

This project implements a JWT (JSON Web Token) authentication service using Spring Boot. It includes endpoints for user registration and authentication.

## Features
- User registration: Allows users to register by providing their first name, last name, email, and password.
- User authentication: Validates user credentials (email and password) and returns a JWT token upon successful authentication.
- JWT token generation: Generates a JWT token containing user information upon registration or authentication.
- Security: Utilizes Spring Security to secure endpoints and manage authentication and authorization.
- Demo endpoint: Includes a demo endpoint for testing purposes.

## Customization and Configuration
The security configuration is organized into classes within the `configuration` package. Key aspects of the configuration include defining a security filter chain bean and customizing security filters.

### Security Filter Chain
The `SecurityConfig` class is responsible for defining the security filter chain bean. Multiple configurations can be defined for different user roles.

### Custom Filters and Authentication Providers
While Spring Security provides default implementations for authentication, custom filters, and authentication providers can be created to meet specific requirements. The application demonstrates how to define custom authentication providers and configure user detail services.

## Further Reading and Resources
For deeper understanding and reference, the following resources are provided:
- [Spring Security Documentation](https://docs.spring.io/spring-security/site/docs/current/reference/html5/)
    - Detailed documentation on Spring Security concepts, including authentication, authorization, and configuration.


## Installation
1. Clone the repository: `git clone <repository_url>`
2. Navigate to the project directory: `cd jwt-authentication-service`
3. Build the project using Maven: `mvn clean package`
4. Run the application: `java -jar target/jwt-authentication-service-<version>.jar`


## Usage

### Registration Endpoint

- **URL:** `POST /api/v1/auth/register`

- **Request body:**
```JSON
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "password123"
}
````
- **Response:**
````JSON
{
  "token": "<JWT_token>"
}

````
### Authentication Endpoint
- **URL:** POST /api/v1/auth/authenticate
- **Request body:**
```JSON
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```
- **Response:**
```JSON
{
  "token": "<JWT_token>"
}
```
### Demo Endpoint
- **URL:** GET /demo/hello
- **Response:**
```Hello from secured endpoint
```
## Security
All endpoints in the AuthenticationController are secured using Spring Security. 
Authentication is performed via JWT tokens.

## License
This project is licensed under the MIT License.