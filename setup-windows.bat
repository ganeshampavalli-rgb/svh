@echo off
REM Sri Venkateswara Home Tutions - Local Development Setup Script (Windows)

echo.
echo ========================================
echo SVH - Local Development Setup
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java 17+ is not installed or not in PATH
    echo Please install Java 17+ from https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
    pause
    exit /b 1
)

REM Check if Node.js is installed
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Node.js is not installed or not in PATH
    echo Please install Node.js from https://nodejs.org/
    pause
    exit /b 1
)

REM Check if PostgreSQL is installed
psql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: PostgreSQL is not installed or not in PATH
    echo Please install PostgreSQL from https://www.postgresql.org/download/windows/
    pause
    exit /b 1
)

echo [✓] All prerequisites installed
echo.
echo ========================================
echo Step 1: Creating Databases
echo ========================================
echo.

REM Create databases
psql -U postgres -c "CREATE DATABASE svh_auth_db;" >nul 2>&1
psql -U postgres -c "CREATE DATABASE svh_course_db;" >nul 2>&1
psql -U postgres -c "CREATE DATABASE svh_enrollment_db;" >nul 2>&1
psql -U postgres -c "CREATE DATABASE svh_learning_db;" >nul 2>&1

echo [✓] Databases created (or already exist)
echo.
echo ========================================
echo Step 2: Running Database Schema
echo ========================================
echo.

REM Import schema to each database
psql -U postgres -d svh_auth_db -f database/schema.sql >nul 2>&1
psql -U postgres -d svh_course_db -f database/schema.sql >nul 2>&1
psql -U postgres -d svh_enrollment_db -f database/schema.sql >nul 2>&1
psql -U postgres -d svh_learning_db -f database/schema.sql >nul 2>&1

echo [✓] Database schemas imported
echo.
echo ========================================
echo Step 3: Building Backend Services
echo ========================================
echo.

setlocal enabledelayedexpansion

for %%S in (auth-service course-service enrollment-service learning-service api-gateway) do (
    echo Building %%S...
    cd backend\%%S
    call mvn clean install -DskipTests >nul 2>&1
    if !errorlevel! neq 0 (
        echo [ERROR] Failed to build %%S
        cd ..\..
        pause
        exit /b 1
    )
    echo [✓] %%S built successfully
    cd ..\..
)

echo.
echo ========================================
echo Step 4: Installing Frontend Dependencies
echo ========================================
echo.

cd frontend
call npm install >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Failed to install frontend dependencies
    cd ..
    pause
    exit /b 1
)
echo [✓] Frontend dependencies installed
cd ..

echo.
echo ========================================
echo Setup Complete!
echo ========================================
echo.
echo Next Steps:
echo 1. Open 5-6 terminal windows
echo 2. Navigate to SVH directory in each terminal
echo 3. Run the following commands in separate terminals:
echo.
echo Terminal 1 (Auth Service):
echo   cd backend\auth-service
echo   mvn spring-boot:run
echo.
echo Terminal 2 (Course Service):
echo   cd backend\course-service
echo   mvn spring-boot:run
echo.
echo Terminal 3 (Enrollment Service):
echo   cd backend\enrollment-service
echo   mvn spring-boot:run
echo.
echo Terminal 4 (Learning Service):
echo   cd backend\learning-service
echo   mvn spring-boot:run
echo.
echo Terminal 5 (API Gateway):
echo   cd backend\api-gateway
echo   mvn spring-boot:run
echo.
echo Terminal 6 (Frontend):
echo   cd frontend
echo   npm start
echo.
echo 4. Open browser and go to: http://localhost:4200
echo.
echo ========================================
pause

