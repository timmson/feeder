val jsoupVersion: String by project
val mockWebserverVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("org.jsoup:jsoup:$jsoupVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.0")

    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebserverVersion")
}
