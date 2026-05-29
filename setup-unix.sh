#!/bin/bash

# Sri Venkateswara Home Tutions - Local Development Setup Script (macOS/Linux)

echo ""
echo "========================================"
echo "SVH - Local Development Setup"
echo "========================================"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java 17+ is not installed"
    echo "Please install Java from https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html"
    exit 1
fi
echo "[✓] Java installed"

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "ERROR: Node.js is not installed"
    echo "Please install Node.js from https://nodejs.org/"
    exit 1
fi
echo "[✓] Node.js installed"

# Check if PostgreSQL is installed
if ! command -v psql &> /dev/null; then
    echo "ERROR: PostgreSQL is not installed"
    echo "macOS: brew install postgresql"
    echo "Linux: sudo apt-get install postgresql"
    exit 1
fi
echo "[✓] PostgreSQL installed"

echo ""
echo "========================================"
echo "Step 1: Starting PostgreSQL"
echo "========================================"
echo ""

# Start PostgreSQL service
if command -v brew &> /dev/null; then
    brew services start postgresql 2>/dev/null || true
else
    sudo systemctl start postgresql 2>/dev/null || true
fi

sleep 2
echo "[✓] PostgreSQL started"

echo ""
echo "========================================"
echo "Step 2: Creating Databases"
echo "========================================"
echo ""

# Create databases
createdb svh_auth_db 2>/dev/null || echo "Database svh_auth_db already exists"
createdb svh_course_db 2>/dev/null || echo "Database svh_course_db already exists"
createdb svh_enrollment_db 2>/dev/null || echo "Database svh_enrollment_db already exists"
createdb svh_learning_db 2>/dev/null || echo "Database svh_learning_db already exists"

echo "[✓] Databases created"

echo ""
echo "========================================"
echo "Step 3: Running Database Schema"
echo "========================================"
echo ""

# Import schema
psql svh_auth_db < database/schema.sql 2>/dev/null
psql svh_course_db < database/schema.sql 2>/dev/null
psql svh_enrollment_db < database/schema.sql 2>/dev/null
psql svh_learning_db < database/schema.sql 2>/dev/null

echo "[✓] Database schemas imported"

echo ""
echo "========================================"
echo "Step 4: Building Backend Services"
echo "========================================"
echo ""

SERVICES=("auth-service" "course-service" "enrollment-service" "learning-service" "api-gateway")

for service in "${SERVICES[@]}"; do
    echo "Building $service..."
    cd backend/$service
    mvn clean install -DskipTests > /dev/null 2>&1
    if [ $? -ne 0 ]; then
        echo "[ERROR] Failed to build $service"
        cd ../..
        exit 1
    fi
    echo "[✓] $service built successfully"
    cd ../..
done

echo ""
echo "========================================"
echo "Step 5: Installing Frontend Dependencies"
echo "========================================"
echo ""

cd frontend
npm install > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "[ERROR] Failed to install frontend dependencies"
    cd ..
    exit 1
fi
echo "[✓] Frontend dependencies installed"
cd ..

echo ""
echo "========================================"
echo "Setup Complete!"
echo "========================================"
echo ""
echo "Next Steps:"
echo "1. Open 6 terminal windows"
echo "2. Navigate to SVH directory in each terminal"
echo "3. Run the following commands in separate terminals:"
echo ""
echo "Terminal 1 (Auth Service):"
echo "  cd backend/auth-service && mvn spring-boot:run"
echo ""
echo "Terminal 2 (Course Service):"
echo "  cd backend/course-service && mvn spring-boot:run"
echo ""
echo "Terminal 3 (Enrollment Service):"
echo "  cd backend/enrollment-service && mvn spring-boot:run"
echo ""
echo "Terminal 4 (Learning Service):"
echo "  cd backend/learning-service && mvn spring-boot:run"
echo ""
echo "Terminal 5 (API Gateway):"
echo "  cd backend/api-gateway && mvn spring-boot:run"
echo ""
echo "Terminal 6 (Frontend):"
echo "  cd frontend && npm start"
echo ""
echo "4. Open browser and go to: http://localhost:4200"
echo ""
echo "========================================"

