FROM eclipse-temurin:23

COPY target/assignment1-1.0-SNAPSHOT-shaded.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]