plugins {
    id("org.springframework.boot") version "3.0.2"
}

dependencies {
    implementation(project(":feeder-stock"))
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.2")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}