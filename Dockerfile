# Utiliser l'image de base officielle de Java
FROM openjdk:17-jdk-alpine

# Créer un répertoire pour l'application
WORKDIR /app

# Copier le fichier jar généré par Maven dans l'image Docker
COPY target/*.jar app.jar

# Exposer le port que l'application va utiliser
EXPOSE 8080

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
