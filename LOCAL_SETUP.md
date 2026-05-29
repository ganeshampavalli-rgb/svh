# Local Development Setup Guide

This guide will help you set up and run the Sri Venkateswara Home Tutions application on your local machine.

## Prerequisites

### Required Software
- **Java 17+** - Download from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- **Node.js 18+** - Download from [nodejs.org](https://nodejs.org/)
- **PostgreSQL 15+** - Download from [postgresql.org](https://www.postgresql.org/download/)
- **Maven 3.9+** - Download from [maven.apache.org](https://maven.apache.org/)
- **Git** - Download from [git-scm.com](https://git-scm.com/)

### Verify Installation
```bash
java -version          # Should show Java 17+
node --version         # Should show Node.js 18+
npm --version          # Should show npm version
mvn --version          # Should show Maven 3.9+
psql --version         # Should show PostgreSQL 15+
```

## Quick Setup (Automated)

### Windows
1. Open PowerShell as Administrator
2. Navigate to the SVH directory
3. Run: `.\setup-windows.bat`
4. Follow the instructions

### macOS/Linux
1. Open Terminal
2. Navigate to the SVH directory
3. Run: `chmod +x setup-unix.sh && ./setup-unix.sh`
4. Follow the instructions

## Manual Setup (Step-by-Step)

### Step 1: Setup Database

#### On Windows:
```powershell
# Create databases
psql -U postgres -c "CREATE DATABASE svh_auth_db;"
psql -U postgres -c "CREATE DATABASE svh_course_db;"
psql -U postgres -c "CREATE DATABASE svh_enrollment_db;"
psql -U postgres -c "CREATE DATABASE svh_learning_db;"

# Import schema
psql -U postgres -d svh_auth_db -f database/schema.sql
psql -U postgres -d svh_course_db -f database/schema.sql
psql -U postgres -d svh_enrollment_db -f database/schema.sql
psql -U postgres -d svh_learning_db -f database/schema.sql
```

#### On macOS/Linux:
```bash
# Start PostgreSQL service
# macOS: brew services start postgresql
# Linux: sudo systemctl start postgresql

# Create databases
createdb svh_auth_db
createdb svh_course_db
createdb svh_enrollment_db
createdb svh_learning_db

# Import schema
psql svh_auth_db < database/schema.sql
psql svh_course_db < database/schema.sql
psql svh_enrollment_db < database/schema.sql
psql svh_learning_db < database/schema.sql
```

### Step 2: Build Backend Services

Open a terminal and navigate to the SVH directory, then run:

```bash
# Auth Service
cd backend/auth-service
mvn clean install -DskipTests

# Course Service
cd ../course-service
mvn clean install -DskipTests

# Enrollment Service
cd ../enrollment-service
mvn clean install -DskipTests

# Learning Service
cd ../learning-service
mvn clean install -DskipTests

# API Gateway
cd ../api-gateway
mvn clean install -DskipTests

# Return to root
cd ../..
```

### Step 3: Install Frontend Dependencies

```bash
cd frontend
npm install
cd ..
```

## Running the Application

### Method 1: Run All Services Locally (Recommended for Development)

You need to open 6 separate terminal windows. In each terminal, navigate to the SVH directory and run:

#### Terminal 1 - Auth Service (Port 8081)
```bash
cd backend/auth-service
mvn spring-boot:run
```

#### Terminal 2 - Course Service (Port 8082)
```bash
cd backend/course-service
mvn spring-boot:run
```

#### Terminal 3 - Enrollment Service (Port 8083)
```bash
cd backend/enrollment-service
mvn spring-boot:run
```

#### Terminal 4 - Learning Service (Port 8084)
```bash
cd backend/learning-service
mvn spring-boot:run
```

#### Terminal 5 - API Gateway (Port 8080)
```bash
cd backend/api-gateway
mvn spring-boot:run
```

Wait for all backend services to start before starting the frontend.

#### Terminal 6 - Angular Frontend (Port 4200)
```bash
cd frontend
npm start
```

Wait for the Angular dev server to start (it will show "Application bundle generated successfully").

### Method 2: Run with Docker Compose (Easiest)

If you have Docker installed:

```bash
# Make sure you're in the SVH root directory
docker-compose up --build

# To stop:
docker-compose down

# To remove volumes (clear database):
docker-compose down -v
```

## Accessing the Application

Once all services are running:

1. **Frontend**: http://localhost:4200
2. **API Gateway**: http://localhost:8080/api
3. **Auth Service**: http://localhost:8081/api
4. **Course Service**: http://localhost:8082/api
5. **Enrollment Service**: http://localhost:8083/api
6. **Learning Service**: http://localhost:8084/api

## Testing the Application

### Register a New User
1. Go to http://localhost:4200
2. Click "Register"
3. Fill in the form:
   - First Name: John
   - Last Name: Doe
   - Email: john@example.com
   - Password: Password@123
   - Role: Student
4. Click "Register"

### Login
1. Go to http://localhost:4200
2. Click "Login"
3. Enter email: john@example.com
4. Enter password: Password@123
5. Click "Login"
6. You should see the dashboard

## Health Check Endpoints

Verify all services are running:

```bash
# Health checks
curl http://localhost:8081/api/auth/health
curl http://localhost:8082/api/courses/health
curl http://localhost:8083/api/enrollments/health
curl http://localhost:8084/api/learning/health
```

All should return: `"Service is running"`

## Troubleshooting

### Database Connection Issues

**Error**: `Connection refused` or `Unable to connect to database`

**Solution**:
```bash
# Check if PostgreSQL is running
# Windows: Check Services app
# macOS: brew services list | grep postgres
# Linux: sudo systemctl status postgresql

# If not running:
# macOS: brew services start postgresql
# Linux: sudo systemctl start postgresql
# Windows: Open Services app and start PostgreSQL
```

### Port Already in Use

**Error**: `Address already in use: bind`

**Solution**:
```bash
# Windows - Find and kill process using port
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# macOS/Linux - Find and kill process
lsof -i :8080
kill -9 <PID>
```

### Maven Build Failures

**Error**: `[ERROR] BUILD FAILURE`

**Solution**:
```bash
# Clean Maven cache
mvn clean install -DskipTests

# Or if that doesn't work:
rm -rf ~/.m2/repository
mvn clean install -DskipTests
```

### NPM Dependencies Issues

**Error**: `npm WARN` or `npm ERR!`

**Solution**:
```bash
cd frontend
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

### CORS Errors in Browser

**Error**: `Access to XMLHttpRequest blocked by CORS policy`

**Solution**:
- Verify API Gateway is running on port 8080
- Check CORS settings in `backend/auth-service/src/main/java/com/svh/auth/config/SecurityConfig.java`
- Verify frontend URL is in allowed origins

### Services Won't Start

**Error**: Services crash immediately after starting

**Solution**:
1. Check logs in terminal
2. Verify database is running: `psql -U postgres -c "\l"`
3. Verify all environment variables are set correctly
4. Check port availability: `netstat -an` (Windows) or `lsof -i` (macOS/Linux)

## Database Management

### Access PostgreSQL CLI

```bash
# Connect to database
psql -U postgres

# List databases
\l

# Connect to specific database
\c svh_auth_db

# List tables
\dt

# Exit
\q
```

### Reset Database

```bash
# Drop and recreate databases
psql -U postgres <<EOF
DROP DATABASE IF EXISTS svh_auth_db;
DROP DATABASE IF EXISTS svh_course_db;
DROP DATABASE IF EXISTS svh_enrollment_db;
DROP DATABASE IF EXISTS svh_learning_db;

CREATE DATABASE svh_auth_db;
CREATE DATABASE svh_course_db;
CREATE DATABASE svh_enrollment_db;
CREATE DATABASE svh_learning_db;
EOF

# Re-import schema
psql -U postgres -d svh_auth_db -f database/schema.sql
psql -U postgres -d svh_course_db -f database/schema.sql
psql -U postgres -d svh_enrollment_db -f database/schema.sql
psql -U postgres -d svh_learning_db -f database/schema.sql
```

## Development Tips

### Hot Reload

**Frontend (Angular)**:
- Changes to TypeScript/HTML/CSS automatically reload
- Modify any file in `frontend/src/` and save - browser will refresh automatically

**Backend (Spring Boot)**:
- Add Spring Boot DevTools for hot reload:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### Debugging

**Backend (IDE)**:
1. Set breakpoint in code
2. Run in Debug mode: `Shift+F10` (IntelliJ) or Debug icon
3. Application will pause at breakpoint

**Frontend (Browser)**:
1. Open DevTools: `F12`
2. Go to "Sources" tab
3. Find TypeScript file
4. Set breakpoint by clicking line number
5. Refresh page to trigger

### Logging

**Backend** - Check terminal output or configure in `application.yml`:
```yaml
logging:
  level:
    root: INFO
    com.svh: DEBUG
```

**Frontend** - Check browser console (`F12` → Console tab)

## Performance Optimization

### Increase Heap Size

```bash
# Linux/macOS
export JAVA_OPTS="-Xmx2048m"
mvn spring-boot:run

# Windows (PowerShell)
$env:JAVA_OPTS="-Xmx2048m"
mvn spring-boot:run
```

### Build Frontend Production Bundle

```bash
cd frontend
npm run build:production
# Output in: dist/svh-frontend
```

## Next Steps

1. After local setup works, read [Architecture Documentation](docs/architecture.md)
2. Review [API Specification](docs/api-spec.md)
3. Check [Deployment Guide](docs/deployment.md) for production deployment
4. Explore the codebase and create test courses

## Support & Resources

- **Spring Boot**: https://spring.io/projects/spring-boot
- **Angular**: https://angular.io/docs
- **PostgreSQL**: https://www.postgresql.org/docs/
- **Maven**: https://maven.apache.org/guides/
- **Node Package Manager**: https://docs.npmjs.com/

---

**Last Updated**: May 2026
**Version**: 1.0

