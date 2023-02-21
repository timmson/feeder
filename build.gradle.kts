import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
    id("org.springframework.boot") version "3.0.2"
}

val springVersion by extra { "6.0.4" }
val springBootVersion by extra { "3.0.2" }
val kotlinVersion by extra { "1.8.10" }
val kotlinCoroutinesVersion by extra { "1.6.4" }
val jaksonVersion by extra { "2.14.1" }
val jsoupVersion by extra { "1.15.3" }
val mockWebserverVersion by extra { "4.10.0" }
val junitVersion by extra { "5.9.2" }
val mockitoVersion by extra { "5.1.1" }
val mockitoKotlinVersion by extra { "4.1.0" }

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")

    group = "ru.timmson.feeder"
    version = "1.0"

    dependencies {
        implementation(kotlin("stdlib-jdk8"))

        implementation("org.springframework:spring-context:$springVersion")

        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

        testImplementation("org.mockito:mockito-core:$mockitoVersion")
        testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
        testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
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

}



