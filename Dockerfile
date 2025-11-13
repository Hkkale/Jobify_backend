# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests



FROM eclipse-temurin:21-jdk
COPY --from=build /target/jobportal_backend-0.0.1-SNAPSHOT.jar JobPortal.jar
ENTRYPOINT ["java", "-jar", "JobPortal.jar"]
EXPOSE 8080



