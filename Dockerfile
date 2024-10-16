FROM eclipse-temurin:21-jdk-alpine

LABEL maintainer="Lecturize It"

COPY target/*.jar app.jar

COPY src/main/resources/static src/main/resources/static
COPY src/main/resources/templates src/main/resources/templates

RUN mkdir -p /app/images

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]