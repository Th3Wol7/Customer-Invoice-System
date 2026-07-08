# SecureCapita — Customer & Invoice Management System

Spring Boot backend for managing customers and invoices behind a JWT-secured API. The project currently implements the authentication/authorization foundation (registration, login, JWT issuance and filtering); the customer and invoice domain modules are the next stage of build-out.

> Active development happens on the `jwtImpl` branch. See [Open Work](#open-work) for the prioritized issue backlog — including a critical JWT filter bug (#1) that must be fixed before any deployment.

## Tech Stack

| Layer | Technology |
|---|---|
| Runtime | Java 19, Spring Boot 3.2.1 |
| Security | Spring Security 6, JJWT 0.11.5 (HS256) |
| Persistence | Spring Data JPA (Hibernate), MySQL |
| Build | Maven (wrapper included) |

## Architecture & Data Flow

```mermaid
flowchart LR
    subgraph Client
        UI[API Consumer / Frontend]
    end

    subgraph SecureCapita["SecureCapita (Spring Boot)"]
        F[JWTAuthenticationFilter]
        AC[AuthenticationController]
        AS[AuthenticationService]
        JS[JwtService]
        UD[UserDetailsService]
        CI[Customer / Invoice modules\n(planned)]
    end

    DB[(MySQL)]

    UI -->|"POST /api/v1/auth/register · /authenticate"| AC
    AC --> AS
    AS -->|BCrypt hash / verify| DB
    AS -->|issue token| JS
    UI -->|"Bearer JWT on protected routes"| F
    F -->|validate token| JS
    F -->|load user| UD
    UD --> DB
    F --> CI
    CI --> DB
```

1. Clients register/authenticate against `/api/v1/auth/**`; passwords are stored BCrypt-hashed and a signed JWT is returned.
2. Every other request passes through `JWTAuthenticationFilter`, which validates the Bearer token, loads the user, and populates the security context.
3. Domain endpoints (customers, invoices) will sit behind that filter once implemented (issue #3).

## Local Setup

### Prerequisites
- Java 19+ (JDK)
- MySQL 8.x running locally
- Maven (or use the bundled `mvnw`)

### Steps

```bash
# 1. Create the database
mysql -u root -p -e "CREATE DATABASE securecapita;"

# 2. Configure environment (see table below) — do NOT rely on the
#    committed properties files, which contain placeholder credentials.

# 3. Run
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

### Environment Variables

Configuration is being migrated from hardcoded properties to environment variables (issue #2). Target contract:

| Variable | Purpose | Example |
|---|---|---|
| `DB_URL` | JDBC URL | `jdbc:mysql://localhost:3306/securecapita` |
| `DB_USERNAME` | Database user | `securecapita_app` |
| `DB_PASSWORD` | Database password | — |
| `JWT_SECRET` | HS256 signing key (256-bit, base64) | generate with `openssl rand -base64 32` |
| `JWT_EXPIRATION_MS` | Token TTL | `86400000` |
| `SPRING_PROFILES_ACTIVE` | `dev` or `prod` | `dev` |

Until #2 lands, `application-dev.properties` holds the datasource settings and `JwtService` holds the signing key — both must be treated as compromised and rotated when externalized.

## Major Decisions

- **Stateless JWT auth** over server-side sessions — the API is designed to be consumed by a separate frontend and to scale horizontally without sticky sessions.
- **BCrypt** for password hashing via Spring Security's `PasswordEncoder`.
- **Profile-split configuration** (`dev`/`prod`) — currently identical; divergence (Flyway migrations, `ddl-auto=validate`, SQL logging off in prod) is tracked in #2.
- **MySQL** as the primary store; the unused PostgreSQL driver and ModelMapper dependencies are slated for removal (#5).

## Deployment

There is no deployment packaging yet — issue #6 tracks Dockerfile + Compose, Spring Actuator health probes, HikariCP pool tuning, Caffeine caching, and a k6 load-test baseline. Do not deploy before issues #1 and #2 are resolved.

## Open Work

Prioritized backlog (deployment-readiness first):

| # | Priority | Issue |
|---|---|---|
| [#1](../../issues/1) | P0 | Fix inverted JWT filter guard — Bearer tokens currently bypass authentication |
| [#2](../../issues/2) | P0 | Externalize secrets and configuration; rotate committed credentials |
| [#3](../../issues/3) | P1 | Build the customer and invoice domain modules |
| [#4](../../issues/4) | P1 | Account-security lifecycle: email verification, password reset, MFA |
| [#5](../../issues/5) | P1 | Toolchain currency (Java LTS, dependency cleanup), tests, CI |
| [#6](../../issues/6) | P2 | Deployment packaging and performance baseline |
