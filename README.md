# LAB: ByteBites Restaurant

## Overview
This is a microservices-based restaurant management system designed using Spring Cloud. It includes centralized configuration, service discovery, an API gateway, and modular services for handling authentication, orders, menus, and more.
## ✅ Services Implemented So Far

### 1. 🧾 Config Server (`config-server`)
- Centralized configuration for all services
- Loads external YAML files from `config-repo/`
- Supports environment variable placeholders via `${}` syntax
- Running on: `http://localhost:8888`

### 2. 🔍 Eureka Discovery Server (`discovery-server`)
- Acts as a service registry for dynamic discovery of services
- UI dashboard: `http://localhost:8761`
- Services automatically register and de-register here

### 3. 🚪 API Gateway (`api-gateway`)
- Entry point for all external requests
- Routes requests to services based on path predicates
- Integrates with Eureka and forwards requests using `lb://<service-name>`
- Running on: `http://localhost:8080`

### 4. 🔐 Auth Service (`auth-service`) _(in progress)_
- Handles user registration, login, and role-based JWT generation
- Uses PostgreSQL (or H2 during dev)
- Registered with Eureka
- Routes configured through API Gateway

## 🛠️ Services To Be Implemented

### 5. 🍴 Restaurant Service
- CRUD operations for restaurants and their menus
- Used by owners/admins

### 6. 🛒 Order Service
- Handles placing and managing customer orders
- Manages order status and history

### 7. 👤 Notifications service
- Handles sending push notification to the customers after order placements

## 🧭 Tech Stack

- Java + Spring Boot
- Spring Cloud Config
- Spring Cloud Netflix Eureka
- Spring Cloud Gateway
- JWT for authentication
- PostgreSQL
- Maven

Make sure to:
1. Start **Config Server**
2. Start **Discovery Server**
3. Start **Auth Service**
4. Start **API Gateway**
5. Test routes via `http://localhost:8080/<path>` (e.g., `/auth/register`)

## 🗂️ Repository Structure (Monorepo)
root/
├── config-repo/ # YAML config files per service
├── config-server/
├── discovery-server/
├── api-gateway/
├── auth-service/
└── ...

## 📝 Notes
- Config changes in `config-repo` require a restart of `config-server` and dependent services
