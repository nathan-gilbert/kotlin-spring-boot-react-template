import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
  val kotlinVersion = "1.4.30"
  val springVersion = "2.4.3"
  val springDepMgmtVersion = "1.0.11.RELEASE"
  val detektVersion = "1.16.0-RC1"
  val dokkaVersion = "1.4.20"
  val flywayVersion = "7.5.3"

  id("org.springframework.boot") version springVersion
  id("io.spring.dependency-management") version springDepMgmtVersion
  @Suppress("StringLiteralDuplication")
  kotlin("jvm") version kotlinVersion
  kotlin("plugin.spring") version kotlinVersion
  kotlin("plugin.allopen") version kotlinVersion
  kotlin("plugin.jpa") version kotlinVersion
  kotlin("kapt") version kotlinVersion
  id("io.gitlab.arturbosch.detekt") version detektVersion
  id("jacoco")
  id("org.jetbrains.dokka") version dokkaVersion
  id("org.flywaydb.flyway") version flywayVersion
}

group = "demo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-mustache")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.postgresql:postgresql")
  implementation("io.springfox:springfox-boot-starter:3.0.0")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.springframework.boot:spring-boot-devtools")
  kapt("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.16.0-RC1")
}

allOpen {
  annotation("javax.persistence.Entity")
  annotation("javax.persistence.Embeddable")
  annotation("javax.persistence.MappedSuperclass")
}

detekt {
  failFast = true // fail build on any finding
  buildUponDefaultConfig = true // preconfigure defaults
  // point to your custom config defining rules to run, overwriting default behavior
  config = files("$projectDir/detekt.yml")
  autoCorrect = true

  reports {
    html.enabled = true // observe findings in your browser with structure and code snippets
    xml.enabled = false // checkstyle like format mainly for integrations like Jenkins
    txt.enabled = false // similar to the console output, contains issue signature to manually edit baseline files
  }
}

jacoco {
  toolVersion = "0.8.6"
}

flyway {
  url = "jdbc:postgresql://localhost:5432/demo"
  user = "nathan"
  password = ""
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    this.jvmTarget = "11"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks {
  withType<io.gitlab.arturbosch.detekt.Detekt> {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    this.jvmTarget = "11"
  }
}

tasks.jacocoTestReport {
  dependsOn(":test")
  reports {
    xml.isEnabled = false
    csv.isEnabled = false
    html.isEnabled = true
    html.destination = file("$buildDir/reports/jacocoHtml")
  }
}

tasks.jacocoTestCoverageVerification {
  violationRules {
    rule {
      limit {
        minimum = "1.0".toBigDecimal()
      }
    }
  }
}

