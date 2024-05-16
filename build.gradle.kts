val spring_boot_version: String by project
val jackson_version: String by project
val flyway_version: String by project
val openapi_version: String by project
val mysql_connector_version: String by project

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    kotlin("plugin.jpa") version "1.9.23"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$spring_boot_version")
    implementation("org.springframework.boot:spring-boot-starter-web:$spring_boot_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")
    implementation("org.flywaydb:flyway-core:$flyway_version")
    implementation("org.flywaydb:flyway-mysql:$flyway_version")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openapi_version")
    runtimeOnly("com.mysql:mysql-connector-j:$mysql_connector_version")

    // spring boot test
    testImplementation("org.springframework.boot:spring-boot-starter-test:$spring_boot_version")
    // testcontainers
    testImplementation("org.testcontainers:junit-jupiter:1.19.8")
    testImplementation("org.testcontainers:mysql:1.19.8")
    // mockk and springmockk
    testImplementation("io.mockk:mockk:1.13.10")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
