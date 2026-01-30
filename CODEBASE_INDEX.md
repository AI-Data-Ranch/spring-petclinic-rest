# Spring PetClinic REST - Comprehensive Codebase Index

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Architecture & Structure](#architecture--structure)
4. [Domain Model](#domain-model)
5. [Data Access Layer](#data-access-layer)
6. [Service Layer](#service-layer)
7. [REST API Layer](#rest-api-layer)
8. [Security](#security)
9. [Database Schema](#database-schema)
10. [Mappers & DTOs](#mappers--dtos)
11. [Testing Infrastructure](#testing-infrastructure)
12. [Configuration](#configuration)
13. [Build & Deployment](#build--deployment)

---

## Project Overview

**Spring PetClinic REST** is a RESTful backend application for managing a veterinary clinic. It provides comprehensive REST APIs for managing owners, pets, veterinarians, visits, and specialties.

- **Purpose**: REST API backend (no UI - consumed by separate Angular frontend)
- **Port**: 9966
- **Base Path**: `/petclinic/`
- **API Documentation**: Swagger UI at `/petclinic/swagger-ui.html`

---

## Technology Stack

### Core Framework
- **Spring Boot**: 3.5.7
- **Spring Framework**: Core IoC, AOP
- **Spring Data JPA**: Repository abstraction
- **Spring Security**: Optional HTTP Basic authentication

### Data Access
- **JPA/Hibernate**: ORM
- **JDBC**: Direct SQL access
- **Spring Data JPA**: Declarative repositories
- **Supported Databases**: H2 (default), HSQLDB, MySQL, PostgreSQL

### API & Documentation
- **SpringDoc OpenAPI**: 2.7.0
- **OpenAPI Generator**: 7.18.0 (DTO generation)
- **Jackson**: JSON serialization
- **MapStruct**: 1.6.3 (Entity ↔ DTO mapping)

### Build & Quality
- **Maven**: 3.8+ (Build tool)
- **Java**: 17+ (Language version)
- **Docker/Jib**: Containerization
- **JMeter**: Performance testing
- **Postman**: API testing

---

## Architecture & Structure

```
src/main/java/org/springframework/samples/petclinic/
├── config/              # Application configuration
│   └── SwaggerConfig.java
├── mapper/              # MapStruct mappers (Entity ↔ DTO)
│   ├── OwnerMapper.java
│   ├── PetMapper.java
│   ├── VetMapper.java
│   ├── VisitMapper.java
│   ├── PetTypeMapper.java
│   ├── SpecialtyMapper.java
│   └── UserMapper.java
├── model/               # Domain entities (JPA)
│   ├── BaseEntity.java
│   ├── NamedEntity.java
│   ├── Person.java
│   ├── Owner.java
│   ├── Pet.java
│   ├── Vet.java
│   ├── Visit.java
│   ├── PetType.java
│   ├── Specialty.java
│   ├── User.java
│   └── Role.java
├── repository/          # Data access layer
│   ├── jpa/            # JPA implementations
│   ├── jdbc/           # JDBC implementations
│   └── springdatajpa/  # Spring Data JPA
├── rest/               # REST API layer
│   ├── controller/     # REST controllers
│   │   ├── OwnerRestController.java
│   │   ├── PetRestController.java
│   │   ├── VetRestController.java
│   │   ├── VisitRestController.java
│   │   ├── PetTypeRestController.java
│   │   ├── SpecialtyRestController.java
│   │   ├── UserRestController.java
│   │   └── RootRestController.java
│   └── advice/         # Exception handling
│       └── ExceptionControllerAdvice.java
├── security/           # Security configuration
│   ├── BasicAuthenticationConfig.java
│   ├── DisableSecurityConfig.java
│   └── Roles.java
├── service/            # Business logic
│   ├── ClinicService.java
│   ├── ClinicServiceImpl.java
│   ├── UserService.java
│   └── UserServiceImpl.java
├── util/               # Utilities
│   ├── CallMonitoringAspect.java
│   └── EntityUtils.java
└── PetClinicApplication.java
```

---

## Domain Model

### Entity Hierarchy
```
BaseEntity (id: Integer)
├── NamedEntity (name: String)
│   ├── PetType
│   ├── Specialty
│   └── Pet
├── Person (firstName, lastName: String)
│   ├── Owner
│   └── Vet
└── Visit
    └── User
        └── Role
```

### Core Entities

#### Owner
- **Table**: `owners`
- **Extends**: Person
- **Fields**: address, city, telephone (10-digit pattern)
- **Relations**: OneToMany → Pet (cascade ALL, EAGER)

#### Pet
- **Table**: `pets`
- **Extends**: NamedEntity
- **Fields**: birthDate (LocalDate)
- **Relations**: 
  - ManyToOne → Owner
  - ManyToOne → PetType
  - OneToMany → Visit (cascade ALL, EAGER)

#### Vet
- **Table**: `vets`
- **Extends**: Person
- **Relations**: ManyToMany → Specialty (EAGER, join table: vet_specialties)

#### Visit
- **Table**: `visits`
- **Extends**: BaseEntity
- **Fields**: date (LocalDate), description (required)
- **Relations**: ManyToOne → Pet

#### User & Role
- **Tables**: `users`, `roles`
- **User Fields**: username (PK), password, enabled
- **Role Fields**: name (with "ROLE_" prefix)
- **Relations**: User OneToMany → Role (cascade ALL, EAGER)

#### PetType & Specialty
- **Tables**: `types`, `specialties`
- **Both**: Extend NamedEntity (simple name-based entities)

### Entity Relationships Diagram
```
Owner (1) ──OneToMany──> Pet (Many)
Pet (Many) ──ManyToOne──> PetType (1)
Pet (1) ──OneToMany──> Visit (Many)
Vet (Many) ──ManyToMany──> Specialty (Many)
User (1) ──OneToMany──> Role (Many)
```

### Key Annotations
- **@MappedSuperclass**: BaseEntity, NamedEntity, Person (no tables)
- **@Entity/@Table**: All concrete entities
- **Cascade.ALL**: Owner→Pets, Pet→Visits, User→Roles
- **FetchType.EAGER**: All OneToMany/ManyToMany relationships
- **Validations**: @NotEmpty on required fields, @Pattern on telephone

---

## Data Access Layer

### Repository Implementations

Three interchangeable implementations (activated via Spring profiles):

| Implementation | Profile | Technology | Characteristics |
|----------------|---------|-----------|-----------------|
| **JDBC** | (default) | NamedParameterJdbcTemplate | Manual SQL, explicit row mapping |
| **JPA** | `jpa` | EntityManager | JPQL queries, JPA annotations |
| **Spring Data JPA** | `spring-data-jpa` | Spring Data Repository | Declarative, auto-generated queries |

### Core Repository Interfaces

#### OwnerRepository
```java
Collection<Owner> findAll()
Owner findById(int id)
void save(Owner owner)
void delete(Owner owner)
Collection<Owner> findByLastName(String lastName)
```

#### PetRepository
```java
Collection<Pet> findAll()
Pet findById(int id)
void save(Pet pet)
void delete(Pet pet)
List<PetType> findPetTypes()
PetType findPetTypeById(int id)
```

#### VetRepository
```java
Collection<Vet> findAll()
Vet findById(int id)
void save(Vet vet)
void delete(Vet vet)
```

#### VisitRepository
```java
Visit findById(int id)
void save(Visit visit)
void delete(Visit visit)
Collection<Visit> findByPetId(Integer petId)
```

#### SpecialtyRepository
```java
Specialty findById(int id)
Collection<Specialty> findAll()
void save(Specialty specialty)
void delete(Specialty specialty)
Set<Specialty> findSpecialtiesByNameIn(Set<String> names)
```

#### UserRepository
```java
void save(User user)
```

---

## Service Layer

### ClinicService
**Implementation**: ClinicServiceImpl  
**Role**: Core business logic facade for all clinic operations

#### Methods
**Pet Management**:
- `findPetById(int id)`: Retrieve pet
- `findAllPets()`: List all pets
- `savePet(Pet pet)`: Create/update pet
- `deletePet(Pet pet)`: Remove pet
- `findPetTypes()`: Get pet type reference data
- `findPetTypeById(int id)`: Get specific pet type

**Visit Management**:
- `findVisitById(int visitId)`: Retrieve visit
- `findAllVisits()`: List all visits
- `saveVisit(Visit visit)`: Create/update visit
- `deleteVisit(Visit visit)`: Remove visit
- `findVisitsByPetId(Integer petId)`: Get pet's visit history

**Vet Management**:
- `findVetById(int id)`: Retrieve vet
- `findAllVets()`: List all vets
- `saveVet(Vet vet)`: Create/update vet
- `deleteVet(Vet vet)`: Remove vet

**Owner Management**:
- `findOwnerById(int id)`: Retrieve owner
- `findAllOwners()`: List all owners
- `saveOwner(Owner owner)`: Create/update owner
- `deleteOwner(Owner owner)`: Remove owner
- `findOwnerByLastName(String lastName)`: Search owners

**Specialty Management**:
- `findSpecialtyById(int id)`: Retrieve specialty
- `findAllSpecialties()`: List all specialties
- `saveSpecialty(Specialty specialty)`: Create/update specialty
- `deleteSpecialty(Specialty specialty)`: Remove specialty
- `findSpecialtiesByNameIn(Set<String> names)`: Batch lookup

#### Transactional Behavior
- **Read-only transactions**: All `find*` methods (`@Transactional(readOnly = true)`)
- **Write transactions**: All `save*` and `delete*` methods (`@Transactional`)
- **Error handling**: Graceful handling of `ObjectRetrievalFailureException`

### UserService
**Implementation**: UserServiceImpl  
**Role**: User registration and authentication support

#### Methods
- `saveUser(User user)`: Register user with role validation
  - Ensures roles have "ROLE_" prefix
  - Establishes bidirectional User-Role relationships
  - Validates user has at least one role

---

## REST API Layer

### Base Configuration
- **Base URL**: `http://localhost:9966/petclinic/api/`
- **CORS**: Enabled on all controllers
- **Security**: Method-level authorization via `@PreAuthorize`

### Controllers & Endpoints

#### OwnerRestController
**Base Path**: `/api/owners`  
**Required Role**: `ROLE_OWNER_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/owners` | List all owners |
| POST | `/api/owners` | Create new owner |
| GET | `/api/owners/{id}` | Get owner by ID |
| PUT | `/api/owners/{id}` | Update owner |
| DELETE | `/api/owners/{id}` | Delete owner |
| GET | `/api/owners/{id}/pets/{petId}` | Get specific pet |
| POST | `/api/owners/{id}/pets` | Add pet to owner |
| PUT | `/api/owners/{id}/pets/{petId}` | Update pet |
| POST | `/api/owners/{id}/pets/{petId}/visits` | Add visit to pet |

#### PetRestController
**Base Path**: `/api/pets`  
**Required Role**: `ROLE_OWNER_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/pets` | List all pets |
| GET | `/api/pets/{id}` | Get pet by ID |
| PUT | `/api/pets/{id}` | Update pet |
| DELETE | `/api/pets/{id}` | Delete pet |

#### VetRestController
**Base Path**: `/api/vets`  
**Required Role**: `ROLE_VET_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/vets` | List all vets |
| POST | `/api/vets` | Create new vet |
| GET | `/api/vets/{id}` | Get vet by ID |
| PUT | `/api/vets/{id}` | Update vet |
| DELETE | `/api/vets/{id}` | Delete vet |

#### VisitRestController
**Base Path**: `/api/visits`  
**Required Role**: `ROLE_OWNER_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/visits` | List all visits |
| POST | `/api/visits` | Create new visit |
| GET | `/api/visits/{id}` | Get visit by ID |
| PUT | `/api/visits/{id}` | Update visit |
| DELETE | `/api/visits/{id}` | Delete visit |

#### PetTypeRestController
**Base Path**: `/api/pettypes`  
**Required Role**: `ROLE_VET_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/pettypes` | List all pet types |
| POST | `/api/pettypes` | Create new pet type |
| GET | `/api/pettypes/{id}` | Get pet type by ID |
| PUT | `/api/pettypes/{id}` | Update pet type |
| DELETE | `/api/pettypes/{id}` | Delete pet type |

#### SpecialtyRestController
**Base Path**: `/api/specialties`  
**Required Role**: `ROLE_VET_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/specialties` | List all specialties |
| POST | `/api/specialties` | Create new specialty |
| GET | `/api/specialties/{id}` | Get specialty by ID |
| PUT | `/api/specialties/{id}` | Update specialty |
| DELETE | `/api/specialties/{id}` | Delete specialty |

#### UserRestController
**Base Path**: `/api/users`  
**Required Role**: `ROLE_ADMIN`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create new user |

#### RootRestController
**Base Path**: `/`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Redirect to Swagger UI |

### Exception Handling
**ExceptionControllerAdvice** provides global exception handling:
- `BindException` → 400 Bad Request
- Returns error details with field-level validation messages

---

## Security

### Configuration
**Enabled via**: `petclinic.security.enable` property (default: `false`)

### Authentication
When enabled:
- **Method**: HTTP Basic Authentication
- **User Store**: JDBC-based (users/roles tables)
- **Password Encoding**: BCrypt
- **User Query**: `SELECT username, password, enabled FROM users WHERE username=?`
- **Authority Query**: `SELECT username, role FROM roles WHERE username=?`

### Authorization
**Method-level security** via `@PreAuthorize`:
- `@EnableMethodSecurity(prePostEnabled = true)`
- Controllers check roles before method execution

### Roles
| Role | Permission Level |
|------|-----------------|
| `ROLE_OWNER_ADMIN` | Owner and pet management |
| `ROLE_VET_ADMIN` | Vet, pet type, and specialty management |
| `ROLE_ADMIN` | Full administrative access (user management) |

### Default Configuration
- **Security**: DISABLED by default
- **CSRF**: Disabled (REST API)
- **Default User**: `admin` (password: bcrypt-hashed in data.sql)

---

## Database Schema

### Tables

#### vets
```sql
id (PK, AUTO_INCREMENT)
first_name (VARCHAR(30))
last_name (VARCHAR(30))
```

#### specialties
```sql
id (PK, AUTO_INCREMENT)
name (VARCHAR(80))
```

#### vet_specialties (Join Table)
```sql
vet_id (FK → vets.id)
specialty_id (FK → specialties.id)
```

#### types
```sql
id (PK, AUTO_INCREMENT)
name (VARCHAR(80))
```

#### owners
```sql
id (PK, AUTO_INCREMENT)
first_name (VARCHAR(30))
last_name (VARCHAR(30))
address (VARCHAR(255))
city (VARCHAR(80))
telephone (VARCHAR(20))
```

#### pets
```sql
id (PK, AUTO_INCREMENT)
name (VARCHAR(30))
birth_date (DATE)
type_id (FK → types.id)
owner_id (FK → owners.id ON DELETE CASCADE)
```

#### visits
```sql
id (PK, AUTO_INCREMENT)
pet_id (FK → pets.id ON DELETE CASCADE)
visit_date (DATE)
description (VARCHAR(255))
```

#### users
```sql
username (PK, VARCHAR(20))
password (VARCHAR(60))
enabled (BOOLEAN)
```

#### roles
```sql
id (PK, AUTO_INCREMENT)
username (FK → users.username)
role (VARCHAR(20))
UNIQUE(username, role)
```

### Database Profiles

| Profile | Database | Connection | Notes |
|---------|----------|------------|-------|
| (default) | H2 | In-memory | Auto-initialized, default |
| `hsqldb` | HSQLDB | In-memory | Alternative in-memory |
| `mysql` | MySQL | localhost:3306 | Docker available |
| `postgres` | PostgreSQL | localhost:5432 | Docker available |

### Seed Data
- 6 veterinarians with specialties
- 3 specialties (radiology, surgery, dentistry)
- 6 pet types (cat, dog, lizard, snake, bird, hamster)
- 10 owners with contact information
- 13 pets
- 4 sample visits
- 1 admin user with all roles

---

## Mappers & DTOs

### MapStruct Mappers
**Location**: `org.springframework.samples.petclinic.mapper`

| Mapper | Conversions | Notes |
|--------|-------------|-------|
| **OwnerMapper** | Owner ↔ OwnerDto<br>OwnerFieldsDto → Owner | Partial updates via FieldsDto |
| **PetMapper** | Pet ↔ PetDto<br>PetFieldsDto → Pet<br>PetType ↔ PetTypeDto | Flattens owner.id ↔ ownerId |
| **VetMapper** | Vet ↔ VetDto<br>VetFieldsDto → Vet | Uses SpecialtyMapper |
| **VisitMapper** | Visit ↔ VisitDto<br>VisitFieldsDto → Visit | Flattens pet.id ↔ petId |
| **PetTypeMapper** | PetType ↔ PetTypeDto<br>PetTypeFieldsDto → PetType | Simple mapper |
| **SpecialtyMapper** | Specialty ↔ SpecialtyDto | Bidirectional |
| **UserMapper** | User ↔ UserDto<br>Role ↔ RoleDto | Security entities |

### DTO Generation
**Source**: OpenAPI specification (`openapi.yml`)  
**Generator**: `openapi-generator-maven-plugin` 7.18.0  
**Output Package**: `org.springframework.samples.petclinic.rest.dto`

**DTO Patterns**:
- **Full DTOs**: Include ID and all fields (e.g., `OwnerDto`)
- **Fields DTOs**: Exclude ID for create/partial update (e.g., `OwnerFieldsDto`)

### Mapping Strategy
- **Component Model**: Spring (MapStruct generates Spring beans)
- **Composition**: Mappers reference other mappers via `@Mapper(uses = ...)`
- **Null Handling**: Default (null → null)
- **Field Flattening**: Nested IDs flattened (e.g., `owner.id` → `ownerId`)

---

## Testing Infrastructure

### Test Organization

#### Service Layer Tests
**Pattern**: Abstract base class + concrete implementations

| Test Class | Profile | Database |
|------------|---------|----------|
| `ClinicServiceH2JdbcTests` | (default) | H2 JDBC |
| `ClinicServiceHsqlJdbcTests` | `hsqldb` | HSQLDB JDBC |
| `ClinicServiceJpaTests` | `jpa` | JPA |
| `ClinicServiceSpringDataJpaTests` | `spring-data-jpa` | Spring Data JPA |
| `UserService*Tests` | (same pattern) | Multiple |

**Base Classes**:
- `AbstractClinicServiceTests` (40+ test methods)
- `AbstractUserServiceTests`

#### REST Controller Tests
**Approach**: MockMvc + Mocked Services

| Test Class | Controller Under Test |
|------------|----------------------|
| `OwnerRestControllerTests` | OwnerRestController |
| `PetRestControllerTests` | PetRestController |
| `VetRestControllerTests` | VetRestController |
| `VisitRestControllerTests` | VisitRestController |
| `PetTypeRestControllerTests` | PetTypeRestController |
| `SpecialtyRestControllerTests` | SpecialtyRestController |
| `UserRestControllerTests` | UserRestController |

#### Other Tests
- `ValidatorTests` - Model validation
- `SpringConfigTests` - Configuration tests

### Testing Technologies

| Framework | Usage |
|-----------|-------|
| **JUnit Jupiter** | Test runner and annotations |
| **Spring Test** | `@SpringBootTest`, MockMvc, TestContext Framework |
| **Mockito** | `@MockitoBean` for service mocking |
| **AssertJ** | Fluent assertions (primary) |
| **Hamcrest** | Matchers for validation |
| **Spring Security Test** | `@WithMockUser` for authentication |
| **Jackson** | JSON serialization in tests |

### Test Patterns
- **BDD Style**: `given()...when()...then()` with Mockito
- **Transactional Tests**: Automatic rollback for data isolation
- **Integration Tests**: Full Spring context with real database
- **Unit Tests**: Mocked dependencies for controllers
- **Data Builders**: Helper methods for creating test data

### Test Utilities
- **ApplicationTestConfig**: Test-specific configuration
- **EntityUtils**: Helper for finding entities in collections
- **MockMvc Builders**: Configured with ExceptionControllerAdvice

---

## Configuration

### Application Properties

#### Default (`application.properties`)
```properties
spring.profiles.active=h2
server.port=9966
server.servlet.context-path=/petclinic
petclinic.security.enable=false
springdoc.swagger-ui.path=/swagger-ui.html
```

#### Database Profiles

**H2** (`application-h2.properties`):
```properties
spring.datasource.url=jdbc:h2:mem:petclinic
spring.jpa.database=H2
```

**HSQLDB** (`application-hsqldb.properties`):
```properties
spring.datasource.url=jdbc:hsqldb:mem:petclinic
spring.jpa.database=HSQL
```

**MySQL** (`application-mysql.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/petclinic
spring.jpa.database=MYSQL
```

**PostgreSQL** (`application-postgres.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/petclinic
spring.jpa.database=POSTGRESQL
```

### Spring Configuration Classes

| Class | Purpose |
|-------|---------|
| **SwaggerConfig** | OpenAPI/Swagger UI configuration |
| **BasicAuthenticationConfig** | Security configuration (when enabled) |
| **DisableSecurityConfig** | No-op security (default) |
| **ApplicationTestConfig** | Test-specific configuration |

### Logging
**Framework**: Logback  
**Config**: `logback.xml`  
**Pattern**: Custom console pattern with timestamp and level

---

## Build & Deployment

### Maven Build
**POM Version**: 3.0.2  
**Java Version**: 17+  
**Spring Boot Version**: 3.5.7

#### Key Plugins
- **spring-boot-maven-plugin**: Executable JAR packaging
- **openapi-generator-maven-plugin**: DTO generation from OpenAPI spec
- **maven-compiler-plugin**: MapStruct annotation processing
- **jib-maven-plugin**: Docker image creation

#### Build Commands
```bash
# Build project
./mvnw clean install

# Run application
./mvnw spring-boot:run

# Run with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=mysql

# Build Docker image
./mvnw jib:dockerBuild
```

### Docker Support
**Docker Compose**: `docker-compose.yml` provides MySQL and PostgreSQL containers

```bash
# Start databases
docker-compose up -d

# Stop databases
docker-compose down
```

### Testing Tools

#### JMeter
**Location**: `src/test/jmeter/`  
**Purpose**: Performance testing scripts

#### Postman
**Location**: `src/test/postman/`  
**Purpose**: API testing collection

### Deployment Profiles
- **Development**: H2 in-memory (default)
- **Testing**: HSQLDB or H2 with test data
- **Production**: MySQL or PostgreSQL with Docker

---

## API Documentation

### OpenAPI Specification
**File**: `src/main/resources/openapi.yml`  
**Version**: OpenAPI 3.0.1  
**Purpose**: API-first design, DTO generation

### Swagger UI
**URL**: `http://localhost:9966/petclinic/swagger-ui.html`  
**Features**:
- Interactive API documentation
- Try-out functionality
- Schema definitions
- Authentication support

### API Design Patterns
- **RESTful**: Standard HTTP methods and status codes
- **HATEOAS**: Self-describing resource links (partial)
- **DTO Pattern**: Separation of API contracts from domain model
- **Error Handling**: Consistent error response format

---

## Key Design Patterns

1. **Repository Pattern**: Abstraction over data access
2. **Service Facade**: ClinicService as single entry point
3. **DTO Pattern**: Separation of concerns (API vs. domain)
4. **Strategy Pattern**: Multiple repository implementations
5. **Template Method**: Abstract test classes with concrete implementations
6. **Dependency Injection**: Spring IoC throughout
7. **Aspect-Oriented Programming**: CallMonitoringAspect for cross-cutting concerns
8. **API-First Design**: OpenAPI specification drives development

---

## Quick Reference

### Common Tasks

**Add new entity**:
1. Create entity in `model/` package
2. Create repository in `repository/springdatajpa/`
3. Add service methods to `ClinicService`
4. Create REST controller in `rest/controller/`
5. Define DTOs in `openapi.yml`
6. Create mapper in `mapper/` package
7. Add tests

**Change database**:
1. Update `spring.profiles.active` in `application.properties`
2. Ensure database is running (Docker Compose for MySQL/PostgreSQL)
3. Restart application

**Enable security**:
1. Set `petclinic.security.enable=true` in `application.properties`
2. Restart application
3. Use HTTP Basic Auth (username: `admin`, password from data.sql)

**Access Swagger UI**:
1. Start application
2. Navigate to `http://localhost:9966/petclinic/swagger-ui.html`

---

## Dependencies Summary

### Core Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter JDBC
- Spring Boot Starter Security
- Spring Boot Starter Test
- SpringDoc OpenAPI UI
- MapStruct
- OpenAPI Generator

### Database Drivers
- H2 Database
- HSQLDB
- MySQL Connector
- PostgreSQL Driver

### Testing
- JUnit Jupiter
- Mockito
- AssertJ
- Spring Security Test

---

**Last Updated**: 2026-01-30  
**Version**: 3.0.2  
**Maintainer**: Spring Samples Team
