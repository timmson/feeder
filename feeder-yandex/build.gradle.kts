val apachePdfBoxVersion: String by project

dependencies {
    implementation(project(":feeder-common"))

    implementation("org.apache.pdfbox:pdfbox:$apachePdfBoxVersion")
}