rootProject.name = "feeder"

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springDependencyManagementPluginVersion: String by settings
    val koverVersion: String by settings

    repositories {
        mavenCentral()
    }

    plugins {
        id("io.spring.dependency-management") version springDependencyManagementPluginVersion
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        id("org.springframework.boot") version springBootVersion
        id("org.jetbrains.kotlinx.kover") version koverVersion
    }
}

include("feeder-calendar")
include("feeder-common")
include("feeder-stock")
include("feeder-web")
