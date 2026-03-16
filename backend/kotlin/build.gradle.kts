buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        // This is what the Gradle Plugin needs to "talk" to Postgres
        classpath("org.flywaydb:flyway-database-postgresql:12.0.2")
    }
}

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    kotlin("plugin.jpa") version "2.3.0"

    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"

    id("org.flywaydb.flyway") version "12.0.2"

    id("org.jlleitschuh.gradle.ktlint") version "14.1.0"
}

group = "com.pokedex"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

repositories {
    mavenCentral()
}

configurations {
    create("flyway")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // Flyway
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    // Hibernate 7 / Postgres
    implementation("org.postgresql:postgresql")
    implementation("io.hypersistence:hypersistence-utils-hibernate-70:3.15.2")

    // Flyway Plugin Driver
    "flyway"("org.postgresql:postgresql:42.7.5")

    // JWT — updated to 0.12.x API
    implementation("io.jsonwebtoken:jjwt-api:0.12.7")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.7")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Springdoc for OpenAPI — 3.0.1 targets Spring Boot 4
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")

    // Cache
    implementation("com.github.ben-manes.caffeine:caffeine")

    // Test Dependencies
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage")
    }
    testImplementation("org.springframework.security:spring-security-test")
}

kotlin {
    jvmToolchain(25)

    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25)
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

flyway {
    url = "jdbc:postgresql://localhost:5432/pokedex"
    user = "pokedex_user"
    password = "pokedex_password"
    cleanDisabled = false

    // Link the plugin to the configuration we created above
    configurations = arrayOf("flyway")
}
