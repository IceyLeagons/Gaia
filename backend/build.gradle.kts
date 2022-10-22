import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
}

group = "net.iceyleagons"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
    implementation("com.google.maps:google-maps-services:2.1.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.json:json:20220924")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}
tasks.register("processFrontendResources", Copy::class) {
    group = "Frontend"
    description = "Process frontend resources"
    dependsOn(project(":frontend").tasks.named("assembleFrontend"))

    from("../frontend/dist/")
    into("../backend/src/main/resources/templates/")
}

tasks.named("processResources") {
    dependsOn(tasks.named("processFrontendResources"))
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        jvmTarget = "17"
        languageVersion = "1.8"
    }
}

tasks.withType<BootJar> {
    manifest {
        attributes["Start-Class"] = "net.iceyleagons.gaia.GaiaApplicationKt"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
