# Customer Invoice API

This project implements a JWT (JSON Web Token) authentication service using Spring Boot. It includes endpoints for user registration and authentication.

## Features
- User registration: Allows users to register by providing their first name, last name, email, and password.
- User authentication: Validates user credentials (email and password) and returns a JWT token upon successful authentication.
- JWT token generation: Generates a JWT token containing user information upon registration or authentication.
- Security: Utilizes Spring Security to secure endpoints and manage authentication and authorization.
- Demo endpoint: Includes a demo endpoint for testing purposes.

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