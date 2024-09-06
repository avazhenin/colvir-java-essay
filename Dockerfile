FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests=true

FROM openjdk:21-jdk
WORKDIR /app
LABEL maintainer=vazhenin
COPY --from=build /app/target/xmlcalendar-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
