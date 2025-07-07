val apachePdfBoxVersion: String by project
val okHttpVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("org.apache.pdfbox:pdfbox:$apachePdfBoxVersion")
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
}