#!/bin/bash

# Change to the current directory (just to be sure)
cd "$(dirname "$0")"

# Run Maven clean install, skipping tests
echo "Running Maven clean install..."
./mvnw clean install -DskipTests

# Set environment variables and run docker-compose
echo "Setting environment variables and starting Docker containers..."
# export MYSQL_DATABASE="lecturizeit"
# export MYSQL_PASSWORD="admin"
docker compose -f compose.yaml -f compose.prod.yaml up --force-recreate

docker compose down

echo "Script completed."