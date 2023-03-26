val mockHttpClientVersion: String by project
val mockWebserverVersion: String by project

dependencies {

    implementation("com.squareup.okhttp3:mockwebserver:$mockWebserverVersion")

    testImplementation("com.squareup.okhttp3:okhttp:$mockWebserverVersion")

}
