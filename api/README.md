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

## TODO

* Add database layer
* Add migration layer