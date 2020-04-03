# Demo API Service

Example backend API service with Kotlin, Springboot, Gradle, JUnit5, Detekt, Jacoco

## Usage

* `./gradlew bootRun` - starts the application
* `./gradlew checks` - runs all checks
* `./gradlew test` - runs all tests
* `./gradlew detekt` - runs linter
* `./gradlew jacocoTestReport` - runs test coverage
* `./gradlew dokka` - generate documentation
* `./gradlew flywayMigrate -i` - migrate the database to the latest version.

## Database Admin

Assumes postgreSQL.

* `createdb demo` -- creates the database, run before initial migration
* `dropdb demo` -- deletes the database

## TODO

* Fix CreatedAt, UpdatedAt, fields etc
