apply(plugin = "org.springframework.boot")

val springBootVersion: String by project
val tgBotAPIVersion: String by project

dependencies {
    implementation(project(":feeder-calendar"))
    implementation(project(":feeder-common"))
    implementation(project(":feeder-cv"))
    implementation(project(":feeder-stock"))
    implementation(project(":feeder-yandex"))

    implementation("com.github.pengrad:java-telegram-bot-api:$tgBotAPIVersion")
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-cache:$springBootVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}
