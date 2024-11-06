# Use a more stable image for ARM64 support
FROM --platform=linux/arm64 openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the pre-built JAR file into the image
COPY target/Foyer-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8089

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
