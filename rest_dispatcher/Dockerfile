# Stage 1: Build
FROM openjdk:19-jdk-slim AS build
WORKDIR /app

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Add jpa_data module
RUN mkdir -p /app/data_common
COPY ./data_common /app/data_common

# Build data_common
RUN mvn -f /app/data_common/pom.xml clean package

# Checking availability JAR-file
RUN ls -l /app/data_common/target

# Install data_common JAR
RUN mvn org.apache.maven.plugins:maven-install-plugin:3.1.0:install-file -Dfile=/app/data_common/target/data_common-0.0.1.jar --debug

# Copy general project
COPY ./rest_dispatcher/src /app/src
COPY ./rest_dispatcher/pom.xml /app/pom.xml

# Build general project without tests
RUN mvn -f /app/pom.xml clean package -DskipTests=true

# Stage 2: Runtime
FROM openjdk:19-jdk-slim

# Copy builded JAR-file from the previous stage
COPY --from=build /app/target/rest_dispatcher-0.0.1.jar app.jar

# Open posr
EXPOSE 8083

# Run jar-file
ENTRYPOINT ["java", "-jar", "app.jar"]







#FROM openjdk:22-jdk-slim AS build
#WORKDIR /app
#
## Install Maven
#RUN apt-get update && \
#    apt-get install -y maven && \
#    rm -rf /var/lib/apt/lists/*
#
## Add jpa_data module
#RUN mkdir -p /app/data_common
#COPY ./data_common /app/data_common
#
## Спочатку будуємо data_common
#RUN mvn -f /app/data_common/pom.xml clean package
#
## Інсталюємо data_common JAR
#RUN mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file -Dfile=/app/data_common/target/data_common-0.0.1.jar --debug
#
## Копіюємо основний проект
#COPY ./rest_dispatcher/src /src
#COPY ./rest_dispatcher/pom.xml /
#
## Будуємо основний проект без тестів
#RUN mvn -f /pom.xml clean package -DskipTests=true
#
## Stage 2: Runtime
#FROM openjdk:22-jdk-slim
#COPY --from=build /target/rest_dispatcher-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8083
#ENTRYPOINT ["java", "-jar", "app.jar"]






#FROM openjdk:22-jdk-slim AS build
#WORKDIR /app
#
## Install Maven
#RUN apt-get update && \
#    apt-get install -y maven && \
#    rm -rf /var/lib/apt/lists/*
#
## Add jpa_data module
#RUN mkdir -p /app/data_common
#COPY ./data_common /app/data_common
#RUN mvn -f /app/data_common/pom.xml clean package
#RUN mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file -Dfile=/app/data_common/target/data_common-0.0.1.jar --debug
#
## Copy project
#COPY ./rest_dispatcher/src /src
#COPY ./rest_dispatcher/pom.xml /
#
## Build project without tests
#RUN mvn -f /pom.xml clean package -DskipTests=true
#
## Stage 2: Runtime
#FROM openjdk:22-jdk-slim
#COPY --from=build /target/rest_dispatcher-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8083
#ENTRYPOINT ["java", "-jar", "app.jar"]







#FROM openjdk:22-jdk-slim AS build
#WORKDIR /app
#
## Install Maven
#RUN apt-get update && \
#    apt-get install -y maven && \
#    rm -rf /var/lib/apt/lists/*
#
## Add jpa_data module
#RUN mkdir -p /app/data_common
#COPY ./data_common /app/data_common
#RUN mvn -f /app/data_common/pom.xml -s mvn clean package
#RUN mvn org.apache.maven.plugins:maven-install-plugin:3.1.1:install-file -Dfile=/app/data_common/target/data_common-0.0.1.jar --debug
#
#
## Copy project
#COPY src /src
#COPY pom.xml /
#
## Build project without tests
#RUN mvn -f /pom.xml clean package -DskipTests=true
#
## Stage 2: Runtime
#FROM openjdk:22-jdk-slim
#COPY --from=build /target/rest_dispatcher-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8083
#ENTRYPOINT ["java", "-jar", "app.jar"]