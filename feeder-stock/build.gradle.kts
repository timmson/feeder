val jacksonVersion: String by project
val jsoupVersion: String by project
val mockWebserverVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")

    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebserverVersion")
}