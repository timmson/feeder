FROM timmson/mbt-platform-v2:java
LABEL maintaner="Krotov Artem <timmson666@mail.ru>"

ARG username

RUN useradd ${username} -s /bin/bash -G sudo -md /home/${username} && \
    sed -i.bkp -e 's/%sudo\s\+ALL=(ALL\(:ALL\)\?)\s\+ALL/%sudo ALL=NOPASSWD:ALL/g' /etc/sudoers && \
    mkdir /app

WORKDIR /app

COPY feeder-web/build/libs/feeder-web-1.0.jar ./feeder-web.jar

RUN chown -R ${username}:${username} .

USER ${username}

CMD ["java", "-jar", "feeder-web.jar"]