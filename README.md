# Microservices Telegram Bot for File/Text/Photo Exchange

## Overview

This is a microservices-based application that facilitates the exchange of files, photos, and text messages
between different microservices. The application integrates a Telegram bot that handles file transfers,
including documents and media files. The core architecture relies on RabbitMQ for message brokering, PostgreSQL
for data persistence, and Docker for containerization...

## Architecture

The application consists of several key microservices, each responsible for different functionalities:

- **Mail Dispatcher**: Sends registration confirmation emails.
- **Management Service**: Manages user-related data and communicates with other services to process incoming messages.
- **REST Dispatcher**: Handles HTTP requests for file uploads and retrievals.
- **Sender Bot**: Acts as the interface between the Telegram bot and the backend, enabling file/text/photo exchanges.
- **PostgreSQL**: Stores persistent data, including user details and file information.
- **RabbitMQ**: Used for inter-service communication and message queuing.

### Key Technologies

- **Spring Boot**: The main framework used for all microservices.
- **RabbitMQ**: For messaging between services, ensuring reliable communication.
- **PostgreSQL**: For database storage, storing user and file metadata.
- **Docker**: To containerize the microservices for easy deployment and scaling.
- **Telegram Bot API**: Used to send and receive messages and files through the Telegram bot.
- **Mail sender**: Used to send messages about registration.
- **Lombok**: For code readability.

## Setup and Configuration

To run the microservices locally, follow the steps below.

### Prerequisites

- **Docker**: Ensure Docker and Docker Compose are installed.
- **Environment Variables**: Set up the necessary environment variables in your `.env` file or `.env.sample` file.

The required environment variables include:

- `MAIL_DISPATCHER_PORT`: Port for the Mail Dispatcher service.
- `MANAGEMENT_PORT`: Port for the Management service.
- `REST_DISPATCHER_PORT`: Port for the REST Dispatcher service.
- `SENDER_BOT_PORT`: Port for the Sender Bot service.
- `POSTGRES_PORT`: Port for the PostgreSQL service.
- `RABBITMQ_PORT`: Port for RabbitMQ.
- Other variables for RabbitMQ, PostgreSQL, Telegram Bot settings, and email configuration.

### Running the Application

1. Clone the repository (terminal):

   ```bash
   git clone https://github.com/RoMANzhula/telegram_bot_rabbitmq.git
   
2. Build data_common module (terminal):
   
   ```bash
   cd data_common
   mvn clean install
   
3. Run docker-compose (terminal) - new window or command: cd ../:

   ```bash
   docker-compose up -d
