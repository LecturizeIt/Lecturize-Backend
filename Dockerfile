FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=target/*.jar

COPY --from=build /app/${JAR_FILE} app.jar
COPY src/main/resources/static src/main/resources/static
COPY src/main/resources/templates src/main/resources/templates

RUN mkdir -p /app/images

ENTRYPOINT ["java","-jar","/app.jar"]