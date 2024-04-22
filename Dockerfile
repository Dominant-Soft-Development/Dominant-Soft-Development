FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/Dominant-Soft-Development-0.0.1-SNAPSHOT.jar Dominant-soft-developer.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "Dominant-soft-developer.jar"]