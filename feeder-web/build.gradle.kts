plugins {
    id("org.springframework.boot") version "3.0.2"
}

dependencies {
    implementation(project(":feeder-moex"))

    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.2")
    implementation(project(mapOf("path" to ":feeder-moex")))
}