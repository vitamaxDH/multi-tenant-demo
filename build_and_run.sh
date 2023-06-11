#!/bin/bash

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "Docker is not installed. Please install Docker and try again."
    exit 1
fi

# Build the Spring Boot project with bootJar
./gradlew bootJar

# Check if the build was successful
if [ $? -eq 0 ]; then
    echo "bootJar build succeeded."

    # Stop and remove Docker containers
    docker-compose down
    if [ $? -eq 0 ]; then
        echo "Docker Compose down succeeded."
    else
        echo "Docker Compose down failed. Please check the error message."
    fi

    # Run Docker Compose
    docker-compose up -d

    # Check if Docker Compose encountered any errors
    if [ $? -eq 0 ]; then
        echo "Docker Compose up succeeded."
    else
        echo "Docker Compose up failed. Please check the error message."
    fi
else
    echo "bootJar build failed. Aborting Docker Compose."
fi
