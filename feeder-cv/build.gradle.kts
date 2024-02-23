val mockHttpClientVersion: String by project
val googleAPIClientVersion: String by project
val googleAPISheetsVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("com.squareup.okhttp3:okhttp:$mockHttpClientVersion")
    implementation("com.google.api-client:google-api-client:$googleAPIClientVersion")
    implementation("com.google.apis:google-api-services-sheets:$googleAPISheetsVersion")

    testImplementation(project(":feeder-test"))
}
