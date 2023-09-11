FROM openjdk:8-jre-alpine
LABEL maintainer="Auth-Service"
ADD target/*.jar app.jar
EXPOSE 9100
ENTRYPOINT [ "java", "-jar", "app.jar" ]
