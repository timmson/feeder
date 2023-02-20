FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine
LABEL maintaner="Krotov Artem <timmson666@mail.ru>"

COPY feeder-web/build/libs/feeder-web.jar app.jar

CMD ["java", "-jar", "app.jar"]