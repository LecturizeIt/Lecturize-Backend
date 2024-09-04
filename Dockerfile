FROM eclipse-temurin:21-jdk-alpine

LABEL maintainer="Lecturize It"

COPY target/*.jar app.jar

RUN mkdir -p /app/images

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]