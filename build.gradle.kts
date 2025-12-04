import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") apply false
    id("org.springframework.boot") apply false
    id("org.jetbrains.kotlinx.kover") apply false
    id("io.spring.dependency-management") apply false
}

val springVersion: String by project
val kotlinCoroutinesVersion: String by project
val jacksonVersion: String by project

val junitVersion: String by project
val mockitoVersion: String by project
val mockitoKotlinVersion: String by project

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "io.spring.dependency-management")

    group = "ru.timmson.feeder"
    version = "1.0"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")

        implementation("org.springframework:spring-context:$springVersion")
        implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")

        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitVersion")

        testImplementation("org.mockito:mockito-core:$mockitoVersion")
        testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
        testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    }

    tasks.withType<JavaCompile>().configureEach {
        options.release = 21
    }

    tasks.withType<KotlinJvmCompile> {
        compilerOptions {
            freeCompilerArgs.add("-Xjsr305=strict")
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}



