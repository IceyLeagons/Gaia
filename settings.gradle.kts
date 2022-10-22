pluginManagement {
    plugins {
        id("org.siouan.frontend-jdk11") version "6.0.0"
    }
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
    }
}
rootProject.name = "junction"
include("backend", "frontend")