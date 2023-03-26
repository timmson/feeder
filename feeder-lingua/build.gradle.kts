val mockHttpClientVersion: String by project
val jsoupVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("com.squareup.okhttp3:okhttp:$mockHttpClientVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")

    testImplementation(project(":feeder-test"))
}

