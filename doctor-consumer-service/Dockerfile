FROM openjdk:8-jre-alpine
LABEL maintainer="Consumer-Service"
ADD target/*.jar app.jar
EXPOSE 9700
ENTRYPOINT [ "java", "-jar", "app.jar" ]
