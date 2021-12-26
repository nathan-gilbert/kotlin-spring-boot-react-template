import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


val detektVersion = "1.19.0"
val mockitoVersion = "4.0.0"
val jupiterVersion = "5.8.2"
val springFoxStarterVersion = "3.0.0"
plugins {
  val kotlinVersion = "1.6.10"
  val springVersion = "2.6.2"
  val springDepMgmtVersion = "1.0.11.RELEASE"
  val dokkaVersion = "1.6.0"
  val flywayVersion = "8.3.0"
  val detektVersion = "1.19.0"

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
  implementation("io.springfox:springfox-boot-starter:$springFoxStarterVersion")
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.springframework.boot:spring-boot-devtools")
  kapt("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.junit.jupiter:junit-jupiter:$jupiterVersion")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

allOpen {
  annotation("javax.persistence.Entity")
  annotation("javax.persistence.Embeddable")
  annotation("javax.persistence.MappedSuperclass")
}

detekt {
  buildUponDefaultConfig = true // preconfigure defaults
  // point to your custom config defining rules to run, overwriting default behavior
  config = files("$projectDir/detekt.yml")
  autoCorrect = true
}

jacoco {
  toolVersion = "0.8.7"
}

flyway {
  url = "jdbc:postgresql://localhost:5432/demo"
  user = "nathan"
  password = ""
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks.withType<Test> {
  useJUnitPlatform()
}

tasks.withType<Detekt>().configureEach {
  // Target version of the generated JVM bytecode. It is used for type resolution.
  jvmTarget = "17"
  reports {
    html.required.set(true) // observe findings in your browser with structure and code snippets
    xml.required.set(false) // checkstyle like format mainly for integrations like Jenkins
    txt.required.set(false) // similar to the console output, contains issue signature to manually edit baseline files
  }
}

tasks.jacocoTestReport {
  dependsOn(":test")
  reports {
    xml.required.set(false)
    csv.required.set(false)
    html.required.set(true)
    html.outputLocation.set(file("$buildDir/reports/jacocoHtml"))
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
