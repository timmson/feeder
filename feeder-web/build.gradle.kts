apply(plugin = "org.springframework.boot")

val springBootVersion: String by rootProject.extra
val tgBotAPIVersion: String by rootProject.extra

dependencies {
    implementation(project(":feeder-calendar"))
    implementation(project(":feeder-common"))
    implementation(project(":feeder-stock"))

    implementation("org.springframework.boot:spring-boot-starter-webflux:$springBootVersion")
    implementation("com.github.pengrad:java-telegram-bot-api:$tgBotAPIVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}