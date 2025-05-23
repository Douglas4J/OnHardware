FROM openjdk:21

COPY target/OnHardware.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]