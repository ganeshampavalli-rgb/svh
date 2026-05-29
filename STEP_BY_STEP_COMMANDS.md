# Sri Venkateswara Home Tutions - Step by Step Commands

## Part 1: Verify Prerequisites (Run ONE at a time)

### Check Java Installation
```
java -version
```
Expected: Shows Java 17 or higher

### Check Node.js Installation
```
node --version
```
Expected: Shows v18 or higher

### Check npm Installation
```
npm --version
```
Expected: Shows npm 10 or higher

### Check Maven Installation
```
mvn --version
```
Expected: Shows Maven 3.9 or higher

### Check PostgreSQL Installation
```
psql --version
```
Expected: Shows PostgreSQL 15 or higher

---

## Part 2: Database Setup (Run ONE at a time)

Navigate to SVH directory:
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
```

### Create Auth Database
```
psql -U postgres -c "CREATE DATABASE svh_auth_db;"
```

### Create Course Database
```
psql -U postgres -c "CREATE DATABASE svh_course_db;"
```

### Create Enrollment Database
```
psql -U postgres -c "CREATE DATABASE svh_enrollment_db;"
```

### Create Learning Database
```
psql -U postgres -c "CREATE DATABASE svh_learning_db;"
```

### Import Schema to Auth Database
```
psql -U postgres -d svh_auth_db -f database/schema.sql
```

### Import Schema to Course Database
```
psql -U postgres -d svh_course_db -f database/schema.sql
```

### Import Schema to Enrollment Database
```
psql -U postgres -d svh_enrollment_db -f database/schema.sql
```

### Import Schema to Learning Database
```
psql -U postgres -d svh_learning_db -f database/schema.sql
```

---

## Part 3: Build Backend Services (Run ONE at a time)

Each of these takes 2-3 minutes (downloads dependencies).

### Navigate to Auth Service
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\auth-service
```

### Build Auth Service
```
mvn clean install -DskipTests
```
Wait for "BUILD SUCCESS"

### Navigate back to Course Service
```
cd ..
cd course-service
```

### Build Course Service
```
mvn clean install -DskipTests
```
Wait for "BUILD SUCCESS"

### Navigate to Enrollment Service
```
cd ..
cd enrollment-service
```

### Build Enrollment Service
```
mvn clean install -DskipTests
```
Wait for "BUILD SUCCESS"

### Navigate to Learning Service
```
cd ..
cd learning-service
```

### Build Learning Service
```
mvn clean install -DskipTests
```
Wait for "BUILD SUCCESS"

### Navigate to API Gateway
```
cd ..
cd api-gateway
```

### Build API Gateway
```
mvn clean install -DskipTests
```
Wait for "BUILD SUCCESS"

### Go Back to Root
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
```

---

## Part 4: Install Frontend Dependencies (Run ONE at a time)

### Navigate to Frontend
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\frontend
```

### Install Dependencies
```
npm install
```
Wait for completion (shows "added XXX packages")

### Go Back to Root
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
```

---

## Part 5: Run Backend Services

⚠️ **IMPORTANT**: Open a NEW PowerShell window for EACH service below

### Service 1 - Auth Service (New PowerShell Window 1)
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\auth-service
mvn spring-boot:run
```
Wait for: `Started AuthServiceApplication in X seconds`

### Service 2 - Course Service (New PowerShell Window 2)
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\course-service
mvn spring-boot:run
```
Wait for: `Started CourseServiceApplication in X seconds`

### Service 3 - Enrollment Service (New PowerShell Window 3)
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\enrollment-service
mvn spring-boot:run
```
Wait for: `Started EnrollmentServiceApplication in X seconds`

### Service 4 - Learning Service (New PowerShell Window 4)
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\learning-service
mvn spring-boot:run
```
Wait for: `Started LearningServiceApplication in X seconds`

### Service 5 - API Gateway (New PowerShell Window 5)
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\api-gateway
mvn spring-boot:run
```
Wait for: `Started ApiGatewayApplication in X seconds`

---

## Part 6: Run Frontend

### Frontend (New PowerShell Window 6)
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\frontend
npm start
```
Wait for: `Application bundle generated successfully`

---

## Part 7: Verify All Services Running

Open a NEW PowerShell window and run these ONE at a time:

### Check Auth Service
```
curl http://localhost:8081/api/auth/health
```
Expected: "Auth Service is running"

### Check Course Service
```
curl http://localhost:8082/api/courses/health
```
Expected: "Course Service is running"

### Check Enrollment Service
```
curl http://localhost:8083/api/enrollments/health
```
Expected: "Enrollment Service is running"

### Check Learning Service
```
curl http://localhost:8084/api/learning/health
```
Expected: "Learning Service is running"

---

## Part 8: Access Application

Once all services show "Started":

1. Open your browser
2. Go to: **http://localhost:4200**
3. You should see the SVH home page

---

## Part 9: Test Registration & Login

### Register New Account
1. Click "Register" button
2. Fill form:
   - First Name: `John`
   - Last Name: `Doe`
   - Email: `john@example.com`
   - Phone: `9876543210`
   - Password: `Password@123`
   - Role: `Student`
3. Click "Register"

### Login
1. Email: `john@example.com`
2. Password: `Password@123`
3. Click "Login"
4. You should see dashboard

---

## Part 10: Stop Application

When done, go to EACH PowerShell window (the 6 that are running services) and:

### In Each Service Window
```
Ctrl + C
```
Then type: `Y` and press Enter

---

## Troubleshooting Commands

### If PostgreSQL doesn't start:
```
# Press Win + R, type:
services.msc
```
Find PostgreSQL, right-click → Start

### If port is already in use (example for port 8080):
```
netstat -ano | findstr :8080
```
Then kill the process:
```
taskkill /PID <PID> /F
```

### If npm install fails:
```
npm cache clean --force
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\frontend
npm install
```

### If Maven build fails:
```
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\auth-service
mvn clean install -DskipTests
```

---

## Summary of Commands in Order

```
1. java -version
2. node --version
3. npm --version
4. mvn --version
5. psql --version
6. cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
7. psql -U postgres -c "CREATE DATABASE svh_auth_db;"
8. psql -U postgres -c "CREATE DATABASE svh_course_db;"
9. psql -U postgres -c "CREATE DATABASE svh_enrollment_db;"
10. psql -U postgres -c "CREATE DATABASE svh_learning_db;"
11. psql -U postgres -d svh_auth_db -f database/schema.sql
12. psql -U postgres -d svh_course_db -f database/schema.sql
13. psql -U postgres -d svh_enrollment_db -f database/schema.sql
14. psql -U postgres -d svh_learning_db -f database/schema.sql

[Then CD into each backend service and run: mvn clean install -DskipTests]

15. cd backend\auth-service && mvn clean install -DskipTests
16. cd ..\..\frontend && npm install
17. cd ..\..

[Then open 6 separate PowerShell windows and run:]

Window 1: cd backend\auth-service && mvn spring-boot:run
Window 2: cd backend\course-service && mvn spring-boot:run
Window 3: cd backend\enrollment-service && mvn spring-boot:run
Window 4: cd backend\learning-service && mvn spring-boot:run
Window 5: cd backend\api-gateway && mvn spring-boot:run
Window 6: cd frontend && npm start
```

---

**Last Updated**: May 2026
**Version**: 1.0

