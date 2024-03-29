# Demo API Service

Example backend API service with Kotlin, Springboot, Gradle, JUnit5, Detekt, Jacoco, Swagger, JPA, Flyway, PostgreSQL

## Usage

- `./gradlew bootRun` - starts the application
- `./gradlew checks` - runs all checks
- `./gradlew test` - runs all tests
- `./gradlew detekt` - runs linter
- `./gradlew jacocoTestReport` - runs test coverage
- `./gradlew dokka` - generate documentation
- `./gradlew flywayMigrate -i` - migrate the database to the latest version.

## Database Admin

Assumes PostgreSQL.

- `createdb demo` -- creates the database, run before initial migration
- `dropdb demo` -- deletes the database
- To create new database migration, add a file to `/src/main/resources/db/migration`

## Swagger Documentation

- `./gradlew bootRun`
- open browser and navigate to <http://localhost:9000/api/swagger-ui/index.html>

## Test the service health endpoint

Run `curl http://localhost:9000/api/health -s | jq`

## TODO

- Add logging
- Add authentication