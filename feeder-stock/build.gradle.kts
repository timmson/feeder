val jaksonVersion: String by rootProject.extra
val jsoupVersion: String by rootProject.extra
val mockWebserverVersion: String by rootProject.extra

dependencies {
    implementation(project(":feeder-common"))

    implementation("com.fasterxml.jackson.core:jackson-databind:$jaksonVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")

    testImplementation("com.squareup.okhttp3:mockwebserver:$mockWebserverVersion")
}