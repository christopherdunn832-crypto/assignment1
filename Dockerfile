FROM maven:3.9-eclipse-temurin-23 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests && ls -la target

FROM eclipse-temurin:23

COPY --from=build /app/target/assignment1-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]