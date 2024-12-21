#!/bin/bash

# Build and package Spring Boot app
./mvnw clean package

# Build Docker image
docker build -t transaction-api . --no-cache

# Start the application Docker container
docker-compose up