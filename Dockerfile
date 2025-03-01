# Stage 1: Build all modules
FROM maven:3.8.6-jdk-17 AS build
WORKDIR /app

# Copy parent POM and all module POMs
COPY pom.xml .
COPY file-api/pom.xml ./file-api/
COPY file-data/pom.xml ./file-data/
COPY file-service/pom.xml ./file-service/

# Download dependencies first (cache-friendly)
RUN mvn -B dependency:go-offline

# Copy source code for all modules
COPY file-api/src ./file-api/src
COPY file-data/src ./file-data/src
COPY file-service/src ./file-service/src

# Package all modules (replace "file-service" with your main module name)
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy JAR from your MAIN module (adjust path if needed)
COPY --from=build /app/file-service/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]