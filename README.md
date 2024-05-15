# Bank Demo

This is a demo application written in Kotlin using Spring Boot.

## Prerequisites

- Java 17
- Kotlin
- Docker

## Running the application

To run the application, first run the following command to start the database:

```
docker-compose up -d
```

Then, run the following command to start the application:

```
./gradlew bootRun
```

The application will be available at `http://localhost:8080`.

## Database

The application uses a MySQL database, running in a Docker container.
Database migrations are managed by Flyway, see the `db/migration` directory for the SQL scripts.

## How to use the application

The application provides a REST API to manage user accounts.

### OpenAPI documentation

The OpenAPI documentation is available at `http://localhost:8080/swagger-ui/index.html`.

### Example requests

You can find example requests in the `Requests.http` file.

