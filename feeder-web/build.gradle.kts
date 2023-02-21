apply(plugin = "org.springframework.boot")

val springBootVersion: String by rootProject.extra

dependencies {
    implementation(project(":feeder-stock"))
    implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}