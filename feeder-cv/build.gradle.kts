val mockHttpClientVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("com.squareup.okhttp3:okhttp:$mockHttpClientVersion")

    //testImplementation(project(":feeder-test"))
}
