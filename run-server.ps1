# LibraryOS Backend Server Startup Script
# This script sets up Maven and starts the Spring Boot server

$mavenVersion = "3.9.6"
$mavenPath = "C:\tools\maven\apache-maven-$mavenVersion\bin"

# Add Maven to PATH
$env:Path = "$mavenPath;" + $env:Path

Write-Host "Starting LibraryOS Backend Server..." -ForegroundColor Green
Write-Host "Server will be available at: http://localhost:8080" -ForegroundColor Cyan
Write-Host ""

# Run Maven Spring Boot
mvn spring-boot:run
