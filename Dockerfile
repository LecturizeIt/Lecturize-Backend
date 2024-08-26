FROM eclipse-temurin:21-jdk-alpine

LABEL maintainer="Lecturize It"

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]