# Stage 1: Build the application
FROM gradle:7.6-jdk17 AS builder
WORKDIR /app

# Copy gradle configuration first for caching
COPY build.gradle settings.gradle ./
COPY gradle gradle
# Copy source code
COPY src src

# Build the application skipping tests to save time
RUN gradle build -x test --no-daemon

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /apps

# Add label
LABEL type="application"

# Copy the jar from the builder stage
COPY --from=builder /app/build/libs/*.jar /apps/app.jar

# Expose port
EXPOSE 8080

# Entrypoint
ENTRYPOINT ["java", "-jar", "/apps/app.jar"]