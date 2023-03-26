val mockHttpClientVersion: String by project
val mockWebserverVersion: String by project

dependencies {

    implementation("com.squareup.okhttp3:mockwebserver:$mockHttpClientVersion")

    testImplementation("com.squareup.okhttp3:okhttp:$mockHttpClientVersion")

}
