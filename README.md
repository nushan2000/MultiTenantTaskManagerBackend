# Multi-Tenant Task Manager — Backend

This repository contains the backend for the Multi-Tenant Task Manager application. It is a Spring Boot application that implements multi-tenancy, authentication (JWT), and basic task/user/tenant management APIs. This README explains the architecture, how to run it (Windows PowerShell), useful endpoints, and troubleshooting.

## Project Overview

- Purpose: Provide a multi-tenant task management REST API where each tenant has isolated data and its own theme handled by the frontend.
- Tech stack: Java 11+ (or compatible), Spring Boot, Spring Data JPA, JWT for authentication, (H2/MySQL/Postgres) as the datasource depending on configuration.
- Key features:
  - Multi-tenancy middleware (tenant context and filter)
  - JWT-based authentication and authorization
  - REST controllers for tenants, users, tasks
  - Repositories and service layers to encapsulate business logic

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT-based authentication
- Role-based access control (Admin/User)
- Tenant ID embedded in JWT tokens
- Secure password hashing

### 🏢 Multi-Tenancy
- Complete data isolation using tenant context
- Tenant-specific branding and themes
- Shared application instance with separate data
- Automatic tenant ID extraction from JWT

### 👥 User Management
- **Admin users:** Manage tenant users and settings
- **Regular users:** Manage personal tasks
- Tenant-bound user registration

### 📋 Task Management
- Create, read, update, delete tasks
- Task assignment to users
- Tenant-specific task lists
- Task status tracking

### 🎨 Tenant-Specific UI
- Dynamic theme switching based on tenant
- Custom branding per organization
- Responsive React interface

### 🔧 Technical Features
- RESTful APIs
- Database migration support
- Comprehensive logging with tenant context
- Error handling and validation

---


## Important files & packages

- `com.example.MultiTenantTaskManagerBackend` — main application class (`MultiTenantTaskManagerBackendApplication.java`)
- `controller/` — REST controllers: `AuthController`, `TaskController`, `TenantController`, `UserController`
- `model/` — domain models: `Tenant`, `User`, `Task`, `AuthRequest`
- `repository/` — Spring Data JPA repositories
- `service/` — business logic services (tenant/user/task)
- `security/` — JWT utilities, filters, and security configuration (`JwtFilter`, `JwtUtil`, `SecurityConfig`)
- `tenant/` — tenant utilities (`TenantContext`, `TenantFilter`) to set the current tenant for a request
- `resources/application.properties` — configuration (DB, JWT secrets, etc.)

## Assumptions

- The tenant is selected per request via a header (for example: `X-Tenant-ID`). The concrete header name is implemented in `TenantFilter`. If you use a different header or URL-based tenant, update the frontend/backend accordingly.
- The JWT token is provided in an `Authorization` header using the `Bearer <token>` scheme.

If these assumptions differ from your current code, adjust the header names or token usage accordingly.

## Quick start (Windows PowerShell)

Open PowerShell in the `MultiTenantTaskManagerBackend` directory and run:

```powershell
# From the backend folder
.
# If using the included wrapper on Windows
.
# Run the app using the Maven wrapper
.
# (PowerShell) use the mvnw.cmd wrapper
.\mvnw.cmd spring-boot:run
```

If you prefer to build and run the generated jar:

```powershell
# Build the project
.\mvnw.cmd -DskipTests package

# Run the jar (adjust the path/name to match the built artifact)
java -jar target\*.jar
```

Note: If you run on non-Windows shells (Git Bash, WSL, Linux), use `./mvnw` instead of `mvnw.cmd`.

## Frontend (local dev)

The repository also contains a frontend at `multi-tenant-frontend/` (React + TypeScript). To run it (from project root):

```powershell
cd multi-tenant-frontend
npm install
npm start
```

The frontend expects the backend to be running and will call the API endpoints. The frontend handles tenant-specific themes (see `src/themes/tenant1.ts`, `src/themes/tenant2.ts`).

## Typical API endpoints

The controllers exposed by the backend include endpoints similar to the list below (adjust paths based on your controllers):

- Authentication
  - `POST /api/auth/login` — authenticate and receive JWT
  - `POST /api/auth/signup` — sign up tenant or user (implementation depends on controllers)
- Tenant management
  - `POST /api/tenants` — create tenant
  - `GET /api/tenants/{id}` — get tenant info
- User management
  - `GET /api/users` — list users
  - `POST /api/users` — create a user
- Task management (tenant-scoped)
  - `GET /api/tasks` — list tasks for current tenant
  - `POST /api/tasks` — create a task
  - `PUT /api/tasks/{id}` — update
  - `DELETE /api/tasks/{id}` — delete

Security: protect routes with JWT. Add the header:

```
Authorization: Bearer <jwt-token>
X-Tenant-ID: <tenant-id>   # or the header your TenantFilter expects
```

## Configuring the database and JWT

Edit `src/main/resources/application.properties` (or use environment variables) to configure:

- Datasource URL, username, password (for MySQL/Postgres) or H2 in-memory settings
- `jwt.secret` or equivalent property used by `JwtUtil`
- Any tenant-specific datasource properties if implemented

For development, an in-memory H2 database makes it easy to test without external dependencies.

## Tests

Run unit/integration tests with Maven:

```powershell
.\mvnw.cmd test
```

## Troubleshooting

- Application fails to start: check `application.properties` for missing DB credentials or invalid JWT secret.
- Tenant not recognized: confirm the header used by the frontend matches the header name in `TenantFilter`.
- 401 Unauthorized: verify you pass the `Authorization` header with a valid JWT token.
- CORS issues: if the frontend is served from a different origin, ensure `SecurityConfig` or Spring's CORS config allows the frontend origin.

## Development notes and next steps

- Add database migrations (Flyway or Liquibase) for schema versioning.
- Add more tests for multi-tenant isolation (integration tests that simulate requests for different tenants).
- Add Docker support (Dockerfile + docker-compose) for local multi-service development.
- Document exact header name used for tenant selection and example requests in `HELP.md` or `README`.

## Contact

If you need help or want me to expand the README with exact fields from `application.properties`, the exact tenant header name, or sample Postman collection, tell me which details you'd like included and I will update the README.

---

(README created by automation — adjust the header name and exact endpoints to match code if different.)
