# Vapor

Vapor is a Spring Boot REST API for managing a game store catalog. The project currently exposes CRUD operations for games, persists data with Spring Data JPA, uses MySQL for persistence, and includes API documentation through Springdoc OpenAPI.

## Tech Stack

- Java 21
- Spring Boot 4.0.5
- Spring Web MVC
- Spring Data JPA
- MySQL
- Jakarta Validation
- Lombok
- MapStruct
- Springdoc OpenAPI / Swagger UI
- JUnit Jupiter
- Mockito
- JaCoCo
- Maven Wrapper

## Project Structure

```text
src
+-- main
|   +-- java/com/loja_de_jogos/vapor
|   |   +-- controllers     # REST controllers
|   |   +-- dtos            # Request and response DTOs
|   |   +-- enums           # Domain enums
|   |   +-- mappers         # MapStruct mappers
|   |   +-- models          # JPA entities
|   |   +-- repositories    # Spring Data repositories
|   |   +-- services        # Business logic
|   +-- resources
|       +-- application.properties
+-- test
    +-- java/com/loja_de_jogos/vapor
```

## Requirements

- JDK 21
- No local Maven installation is required. Use the included Maven Wrapper:
  - Windows: `mvnw.cmd`
  - Linux/macOS: `./mvnw`

## Configuration

The application is configured in `src/main/resources/application.properties`.

Current local database configuration:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/vapor_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC}
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=${DB_USERNAME:vapor}
spring.datasource.password=${DB_PASSWORD:vapor}
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=validate
```

By default, the app connects to a local MySQL database named `vapor_db` using the `vapor` user and `vapor` password. You can override the connection with the `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` environment variables.

You can start a local MySQL instance with Docker Compose:

```powershell
docker compose up -d
```

If the local database was created before the Flyway migrations were added, reset the Docker volume and start it again:

```powershell
docker compose down -v
docker compose up -d
```

If you are using your own MySQL server instead, create the database before running the application:

```sql
CREATE DATABASE vapor_db;
CREATE USER 'vapor'@'%' IDENTIFIED BY 'vapor';
GRANT ALL PRIVILEGES ON vapor_db.* TO 'vapor'@'%';
```

Database schema changes are managed by Flyway migrations under:

```text
src/main/resources/db/migration/
```

The initial migrations create the `games` and `game_genres` tables and insert 20 sample games. The `normalized_id` column is nullable for now.

## Running the Application

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux/macOS:

```bash
./mvnw spring-boot:run
```

The API runs at:

```text
http://localhost:8080
```

## API Documentation

When the application is running, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

The OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

## Game API

Base path:

```text
/games
```

### Endpoints

| Method | Path | Description |
| --- | --- | --- |
| `POST` | `/games` | Create a game |
| `GET` | `/games` | List all games |
| `GET` | `/games/{id}` | Get a game by ID |
| `PUT` | `/games/{id}` | Update a game |
| `DELETE` | `/games/{id}` | Delete a game |

### Request Body

`POST /games` and `PUT /games/{id}` expect the same payload shape:

```json
{
  "name": "Hollow Knight",
  "image": "https://example.com/hollow-knight.jpg",
  "description": "A challenging atmospheric action adventure through a vast interconnected world.",
  "price": 29.99,
  "genre": ["RPG"],
  "releaseDate": "2017-02-24T00:00:00.000+00:00",
  "userRating": 9.5,
  "developer": "Team Cherry",
  "publisher": "Team Cherry",
  "hasDiscount": false,
  "ageRating": "TEN"
}
```

### Validation Rules

- `name`: required and cannot be blank
- `image`: required and cannot be blank
- `description`: required, cannot be blank, must have 20 to 400 characters
- `price`: required and cannot be negative
- `genre`: required and cannot be empty
- `releaseDate`: required
- `userRating`: cannot be negative when provided
- `developer`: required and cannot be blank
- `publisher`: required and cannot be blank
- `ageRating`: required

### Available Genres

```text
RPG
FPS
SURVIVOR_HORROR
SPORT
MOBA
TURN_BASED
```

### Available Age Ratings

```text
GENERAL_AUDIENCE
SIX
TEN
TWELVE
FOURTEEN
SIXTEEN
EIGHTEEN
```

## Building

Compile and package the application:

```powershell
.\mvnw.cmd clean package
```

On Linux/macOS:

```bash
./mvnw clean package
```

The packaged application is generated under `target/`.

Run the packaged JAR:

```powershell
java -jar target\vapor-0.0.1-SNAPSHOT.jar
```

## Testing

Run the test suite:

```powershell
.\mvnw.cmd test
```

On Linux/macOS:

```bash
./mvnw test
```

The project is configured with:

- JUnit Jupiter for unit and integration tests
- Mockito for mocks and test doubles
- Spring Boot test support for application context and web/data-layer testing

## Code Coverage

JaCoCo is configured through the Maven build.

Generate tests and coverage reports:

```powershell
.\mvnw.cmd verify
```

On Linux/macOS:

```bash
./mvnw verify
```

Coverage reports are generated in:

```text
target/site/jacoco/
```

Open the HTML report directly in a browser:

```text
target/site/jacoco/index.html
```

On Windows, you can open it from PowerShell:

```powershell
start target\site\jacoco\index.html
```

The XML report, useful for CI tools such as GitHub Actions, is generated at:

```text
target/site/jacoco/jacoco.xml
```

## Common Development Commands

```powershell
# Run the application
.\mvnw.cmd spring-boot:run

# Run tests
.\mvnw.cmd test

# Build the application
.\mvnw.cmd clean package

# Run tests and generate coverage
.\mvnw.cmd verify
```

## Future CI

GitHub Actions can later use the Maven Wrapper to build, test, and generate coverage:

```bash
./mvnw verify
```

This command runs the test suite and produces the JaCoCo XML report that coverage services and CI checks can consume.
