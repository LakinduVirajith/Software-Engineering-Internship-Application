# Step 1: Use Maven to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Step 2: Use a lightweight JDK image to run the application
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/social-media-backend-1.0.0.jar /app/social-media-backend-1.0.0.jar

# Expose the application port
EXPOSE 5000

# Set the entry point for the application
ENTRYPOINT ["java", "-jar", "social-media-backend-1.0.0.jar"]
