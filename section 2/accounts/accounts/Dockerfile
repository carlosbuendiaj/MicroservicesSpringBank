#Start wutg a vase unage containing Java runtime
FROM openjdk:17-jdk-slim
#Who maintains the app
LABEL authors="Carlos"

#Please take the jar, and copy in the docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar

#When the docker image create a docker container for this microservice, will create an accounts-0.0.1-SNAPSHOT.jar, in jar format, java language
ENTRYPOINT ["java", "-jar", "accounts-0.0.1-SNAPSHOT.jar"]