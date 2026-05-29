cd # Quick Copy-Paste Commands - Run One by One

## PHASE 1: VERIFY PREREQUISITES (Takes 1 minute)

Open PowerShell and run these commands ONE at a time:

```powershell
java -version
```
**Expected**: Java 17 or higher

```powershell
node --version
```
**Expected**: v18.0.0 or higher

```powershell
npm --version
```
**Expected**: 10.0.0 or higher

```powershell
mvn --version
```
**Expected**: Apache Maven 3.9+ 

```powershell
psql --version
```
**Expected**: psql (PostgreSQL) 15 or higher

✅ **If all 5 show versions, you're ready to continue!**

---

## PHASE 2: SETUP DATABASES (Takes 2 minutes)

Open PowerShell and run these commands:

```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
```

Create databases (run each one separately):

```powershell
psql -U postgres -c "CREATE DATABASE svh_auth_db;"
```

```powershell
psql -U postgres -c "CREATE DATABASE svh_course_db;"
```

```powershell
psql -U postgres -c "CREATE DATABASE svh_enrollment_db;"
```

```powershell
psql -U postgres -c "CREATE DATABASE svh_learning_db;"
```

Import schemas (run each one separately):

```powershell
psql -U postgres -d svh_auth_db -f database/schema.sql
```

```powershell
psql -U postgres -d svh_course_db -f database/schema.sql
```

```powershell
psql -U postgres -d svh_enrollment_db -f database/schema.sql
```

```powershell
psql -U postgres -d svh_learning_db -f database/schema.sql
```

✅ **If no errors, databases are ready!**

---

## PHASE 3: BUILD BACKEND SERVICES (Takes 15-20 minutes total)

Still in same PowerShell window:

### Build Auth Service (takes 3-4 min)
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\auth-service
mvn clean install -DskipTests
```
**Wait for "BUILD SUCCESS"**

### Build Course Service (takes 2-3 min)
```powershell
cd ..\course-service
mvn clean install -DskipTests
```
**Wait for "BUILD SUCCESS"**

### Build Enrollment Service (takes 2-3 min)
```powershell
cd ..\enrollment-service
mvn clean install -DskipTests
```
**Wait for "BUILD SUCCESS"**

### Build Learning Service (takes 2-3 min)
```powershell
cd ..\learning-service
mvn clean install -DskipTests
```
**Wait for "BUILD SUCCESS"**

### Build API Gateway (takes 2-3 min)
```powershell
cd ..\api-gateway
mvn clean install -DskipTests
```
**Wait for "BUILD SUCCESS"**

### Go back to root
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
```

✅ **All backend services built!**

---

## PHASE 4: INSTALL FRONTEND (Takes 5 minutes)

Still in PowerShell:

```powershell
cd frontend
npm install
```
**Wait for completion (see "added XXX packages")**

```powershell
cd ..
```

✅ **Frontend ready!**

---

## PHASE 5: START ALL SERVICES (Takes 2 minutes to start all)

### ⚠️ IMPORTANT: Open a NEW PowerShell window for EACH service!

**Open PowerShell Window #1 and run:**
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\auth-service
mvn spring-boot:run
```
**Wait until you see a message like "Started AuthServiceApplication in 5.xxx seconds"**

---

**Open PowerShell Window #2 and run:**
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\course-service
mvn spring-boot:run
```
**Wait until it shows "Started CourseServiceApplication"**

---

**Open PowerShell Window #3 and run:**
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\enrollment-service
mvn spring-boot:run
```
**Wait until it shows "Started EnrollmentServiceApplication"**

---

**Open PowerShell Window #4 and run:**
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\learning-service
mvn spring-boot:run
```
**Wait until it shows "Started LearningServiceApplication"**

---

**Open PowerShell Window #5 and run:**
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\backend\api-gateway
mvn spring-boot:run
```
**Wait until it shows "Started ApiGatewayApplication"**

---

**Open PowerShell Window #6 and run:**
```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH\frontend
npm start
```
**Wait until you see "Application bundle generated successfully"**

✅ **All 6 services are now running!**

---

## PHASE 6: ACCESS APPLICATION

Once all 6 services show "Started" messages:

1. Open your web browser (Chrome, Firefox, Edge, etc.)
2. Go to: **http://localhost:4200**
3. You should see the SVH home page with navigation

---

## PHASE 7: TEST THE APPLICATION

### Step 1: Register Account
1. Click the "Register" button
2. Fill in the form:
   - **First Name**: John
   - **Last Name**: Doe
   - **Email**: test@example.com
   - **Phone**: 9876543210
   - **Password**: Password@123
   - **Role**: Student
3. Click "Register" button
4. You should see a success message

### Step 2: Login
1. You'll be automatically logged in after registration
2. Or click "Login" and use:
   - **Email**: test@example.com
   - **Password**: Password@123
3. You should see the Dashboard

### Step 3: Explore
- Browse the home page
- Try registration with different users
- Check the dashboard

✅ **Application is working!**

---

## PHASE 8: VERIFY ALL SERVICES (Optional - for testing)

Open a NEW PowerShell window and run these (one at a time):

```powershell
curl http://localhost:8081/api/auth/health
```
**Should show**: "Auth Service is running"

```powershell
curl http://localhost:8082/api/courses/health
```
**Should show**: "Course Service is running"

```powershell
curl http://localhost:8083/api/enrollments/health
```
**Should show**: "Enrollment Service is running"

```powershell
curl http://localhost:8084/api/learning/health
```
**Should show**: "Learning Service is running"

✅ **All services healthy!**

---

## Service URLs When Running

| Service | URL |
|---------|-----|
| Frontend | http://localhost:4200 |
| Auth API | http://localhost:8081/api |
| Course API | http://localhost:8082/api |
| Enrollment API | http://localhost:8083/api |
| Learning API | http://localhost:8084/api |
| API Gateway | http://localhost:8080/api |

---

## STOPPING EVERYTHING

When you want to stop:

**In each of the 6 PowerShell windows (where services are running), press:**
```
Ctrl + C
```

Then type: `Y` and press Enter

All services will stop.

---

## COMMON ISSUES

### "Connection refused" when starting services
**Solution**: PostgreSQL is not running
```powershell
# Start PostgreSQL service
# Press Win + R and type: services.msc
# Find PostgreSQL and click Start
```

### "Port already in use"
**Solution**: Find and close the process
```powershell
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Services crash immediately
**Check**: 
1. Is PostgreSQL running?
2. Are all databases created?
3. Are schemas imported?

### npm install takes too long
**Solution**:
```powershell
npm cache clean --force
cd frontend
npm install
```

---

## NEXT STEPS

After everything is running:

1. Create a test course
2. Explore the dashboard
3. Try enrolling in courses
4. Read the documentation:
   - PROJECT_SUMMARY.md
   - docs/architecture.md
   - docs/api-spec.md
5. Deploy to production (docs/deployment.md)

---

**Total Time to Setup**: ~40-50 minutes (first time)
**Status**: ✅ Ready to use!

---

**Last Updated**: May 2026
**Version**: 1.0

