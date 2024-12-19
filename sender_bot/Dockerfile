# Stage 1: build
FROM openjdk:19-jdk-slim AS build
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy project
COPY ./sender_bot/src /src
COPY ./sender_bot/pom.xml /

# Build project without tests
RUN mvn -f /pom.xml clean package -DskipTests=true

# Stage 2: Runtime
FROM openjdk:19-jdk-slim

# Copy builded JAR-file from the previous stage
COPY --from=build /target/sender_bot-0.0.1.jar app.jar

# Open posr
EXPOSE 8080

# run jar-file
ENTRYPOINT ["java", "-jar", "app.jar"]

