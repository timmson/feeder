val apachePdfBoxVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("org.apache.pdfbox:pdfbox:$apachePdfBoxVersion")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.1")
}