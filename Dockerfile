FROM maven:3.6.3-openjdk-17-slim

RUN mkdir url_shortcut

WORKDIR url_shortcut

COPY . .

RUN mvn package -Dmaven.test.skip=true

CMD ["java","-jar", "target/urlshortcut-1.0.war"]