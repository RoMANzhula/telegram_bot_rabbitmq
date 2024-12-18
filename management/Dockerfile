# Stage 1: Build
FROM openjdk:19-jdk-slim AS build
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Copy data_common module
RUN mkdir -p /app/data_common
COPY ./data_common /app/data_common

# Build data_common
RUN mvn -f /app/data_common/pom.xml clean package

# Check creating JAR-file
RUN ls -l /app/data_common/target

# Install data_common JAR
RUN mvn org.apache.maven.plugins:maven-install-plugin:3.1.0:install-file -Dfile=/app/data_common/target/data_common-0.0.1.jar --debug

# Copy general project management
COPY ./management/src /app/src
COPY ./management/pom.xml /app/pom.xml

# Build management without tests
RUN mvn -f /app/pom.xml clean package -DskipTests=true

# Stage 2: Runtime
FROM openjdk:19-jdk-slim
COPY --from=build /app/target/management-0.0.1.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]

