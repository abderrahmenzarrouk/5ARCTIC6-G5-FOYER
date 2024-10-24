
FROM openjdk:17


WORKDIR /app


COPY target/Foyer-0.0.1-SNAPSHOT.jar /app/


EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]
