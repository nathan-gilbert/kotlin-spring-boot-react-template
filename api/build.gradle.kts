import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "2.2.6.RELEASE"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
  @Suppress("StringLiteralDuplication")
  kotlin("jvm") version "1.3.70"
  kotlin("plugin.spring") version "1.3.70"
  kotlin("plugin.allopen") version "1.3.70"
  kotlin("plugin.jpa") version "1.3.70"
  kotlin("kapt") version "1.3.70"
  id("io.gitlab.arturbosch.detekt") version ("1.7.1")
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
  runtimeOnly("com.h2database:h2")
  runtimeOnly("org.springframework.boot:spring-boot-devtools")
  kapt("org.springframework.boot:spring-boot-configuration-processor")
  testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.7.1")
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
