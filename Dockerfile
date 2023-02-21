FROM --platform=linux/amd64 eclipse-temurin:17-jdk-alpine
LABEL maintaner="Krotov Artem <timmson666@mail.ru>"

ENV TZ=Europe/Moscow
RUN apk add tzdata && cp /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && apk del tzdata

COPY feeder-web/build/libs/feeder-web.jar app.jar

CMD ["java", "-jar", "app.jar"]