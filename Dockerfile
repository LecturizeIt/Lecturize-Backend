FROM amazoncorretto:21-alpine-jdk

LABEL maintainer="Lecturize It"

COPY target/Lecturize-Backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]