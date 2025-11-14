# Change to the current directory (just to be sure)
Set-Location $PSScriptRoot

# Run Maven clean install, skipping tests
Write-Host "Running Maven clean install..."
./mvnw clean install -DskipTests

# Set environment variables and run docker-compose
Write-Host "Setting environment variables and starting Docker containers..."
# $env:MYSQL_DATABASE = "lecturizeit"
# $env:MYSQL_PASSWORD = "admin"
docker compose -f compose.yaml up -d --build --force-recreate

Write-Host "Script completed."

#docker compose down --remove-orphans