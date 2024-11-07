FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copier le fichier JAR dans l'image
COPY target/Foyer-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port que l'application écoute
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
