# Step 1: Build stage using a valid Maven image
FROM maven:3.8.3-openjdk-17 AS build

# Define working directory
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .

# Download dependencies offline (for faster builds)
RUN mvn dependency:go-offline

# Copy source files
COPY src /app/src

# Build the JAR file
RUN mvn clean package -DskipTests

# Step 2: Final stage with OpenJDK runtime image
FROM openjdk:17-jdk-slim

# Define working directory
WORKDIR /app

COPY .env .env

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar /app/email-service.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/email-service.jar"]