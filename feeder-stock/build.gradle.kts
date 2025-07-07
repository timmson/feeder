val jacksonVersion: String by project
val mockWebserverVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")

    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebserverVersion")
}
