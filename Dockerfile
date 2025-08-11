# Stage 1: Build with Maven and Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy all source code
COPY . .

# Build application (skip tests for speed)
RUN mvn clean package -DskipTests

# Stage 2: Run with smaller JDK
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
