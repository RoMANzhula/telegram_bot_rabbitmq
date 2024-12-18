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

# Install JAR to local Maven repository
RUN mvn install:install-file \
    -Dfile=/app/data_common/target/data_common-0.0.1.jar \
    -DgroupId=org.romanzhula \
    -DartifactId=data_common \
    -Dversion=0.0.1 \
    -Dpackaging=jar \
    -DgeneratePom=true

# Copy mail_dispatcher
COPY ./mail_dispatcher /app/mail_dispatcher

# Build mail_dispatcher without tests
RUN mvn -f /app/mail_dispatcher/pom.xml clean package -DskipTests=true

# Stage 2: Runtime
FROM openjdk:19-jdk-slim
WORKDIR /app

# Copy builded JAR-file from the previous stage
COPY --from=build /app/mail_dispatcher/target/mail_dispatcher-0.0.1.jar app.jar

# Open port
EXPOSE 8084

# Run JAR-file
ENTRYPOINT ["java", "-jar", "app.jar"]

