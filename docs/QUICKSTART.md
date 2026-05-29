# Quick Start Guide - Sri Venkateswara Home Tutions

## Local Development Setup

### Prerequisites
- Java 17+
- Node.js 18+ with npm
- PostgreSQL 15+
- Git
- Maven 3.9+
- Your favorite IDE (IntelliJ IDEA, VS Code, etc.)

### Step 1: Clone Repository
```bash
git clone https://github.com/your-username/SVH.git
cd SVH
```

### Step 2: Setup Database

#### Windows:
```bash
# Install PostgreSQL if not already installed
# Create databases
psql -U postgres -c "CREATE DATABASE svh_auth_db;"
psql -U postgres -c "CREATE DATABASE svh_course_db;"
psql -U postgres -c "CREATE DATABASE svh_enrollment_db;"
psql -U postgres -c "CREATE DATABASE svh_learning_db;"

# Import schema
psql -U postgres svh_auth_db < database/schema.sql
psql -U postgres svh_course_db < database/schema.sql
psql -U postgres svh_enrollment_db < database/schema.sql
psql -U postgres svh_learning_db < database/schema.sql
```

#### macOS/Linux:
```bash
# Using homebrew (macOS)
brew install postgresql

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

### Step 3: Run Backend Services

#### Terminal 1 - Auth Service:
```bash
cd backend/auth-service
mvn clean install
mvn spring-boot:run
# Will run on http://localhost:8081
```

#### Terminal 2 - Course Service:
```bash
cd backend/course-service
mvn clean install
mvn spring-boot:run
# Will run on http://localhost:8082
```

#### Terminal 3 - Enrollment Service:
```bash
cd backend/enrollment-service
mvn clean install
mvn spring-boot:run
# Will run on http://localhost:8083
```

#### Terminal 4 - Learning Service:
```bash
cd backend/learning-service
mvn clean install
mvn spring-boot:run
# Will run on http://localhost:8084
```

#### Terminal 5 - API Gateway:
```bash
cd backend/api-gateway
mvn clean install
mvn spring-boot:run
# Will run on http://localhost:8080
```

### Step 4: Run Frontend

#### Terminal 6:
```bash
cd frontend
npm install
npm start
# Will run on http://localhost:4200
```

### Step 5: Test the Application

1. Open browser: http://localhost:4200
2. Click "Register" and create a new account
3. Login with your credentials
4. You should see the dashboard

## Default Environment Variables

Create `.env` file in each service directory:

### backend/auth-service/.env
```
DB_URL=jdbc:postgresql://localhost:5432/svh_auth_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
JWT_SECRET=your-development-secret-key-at-least-256-bits
JWT_EXPIRATION=86400
JWT_REFRESH_EXPIRATION=604800
CORS_ORIGINS=http://localhost:4200,http://localhost:3000
```

### backend/course-service/.env
```
DB_URL=jdbc:postgresql://localhost:5432/svh_course_db
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

Similar for other services with their respective databases.

### frontend/.env
```
NG_APP_API_URL=http://localhost:8080/api
NG_APP_ENV=development
```

## Common Commands

### Maven (Backend)
```bash
mvn clean              # Clean build directory
mvn compile           # Compile source code
mvn test              # Run tests
mvn package           # Create JAR
mvn spring-boot:run   # Run Spring Boot app
mvn install           # Install dependencies
```

### npm (Frontend)
```bash
npm install           # Install dependencies
npm start             # Run dev server (ng serve)
npm run build         # Build for production
npm test              # Run tests
npm run lint          # Check code quality
```

## Debugging

### Backend Debugging (IntelliJ IDEA)
1. Set breakpoint in code
2. Run → Debug 'AuthServiceApplication'
3. Application will pause at breakpoint

### Frontend Debugging (Chrome DevTools)
1. Open Chrome DevTools (F12)
2. Go to Sources tab
3. Open TypeScript files
4. Set breakpoints
5. Refresh page to trigger

### Logs
- Backend: Check terminal output
- Frontend: Check browser console (F12)

## Testing

### Backend Unit Tests
```bash
cd backend/auth-service
mvn test
```

### Frontend Unit Tests
```bash
cd frontend
npm test
```

## Project Structure

```
SVH/
├── backend/
│   ├── api-gateway/          # API Gateway (8080)
│   ├── auth-service/         # Auth Service (8081)
│   ├── course-service/       # Course Service (8082)
│   ├── enrollment-service/   # Enrollment Service (8083)
│   ├── learning-service/     # Learning Service (8084)
│   └── .gitignore
├── frontend/
│   ├── src/
│   │   ├── app/
│   │   │   ├── features/
│   │   │   │   ├── auth/
│   │   │   │   ├── courses/
│   │   │   │   ├── dashboard/
│   │   │   │   └── home/
│   │   │   ├── core/
│   │   │   │   ├── services/
│   │   │   │   └── interceptors/
│   │   │   └── shared/
│   │   ├── styles.scss
│   │   └── main.ts
│   ├── angular.json
│   ├── package.json
│   ├── tsconfig.json
│   └── .gitignore
├── database/
│   ├── schema.sql
│   └── seed-data.sql
├── docs/
│   ├── architecture.md
│   ├── api-spec.md
│   └── deployment.md
└── README.md
```

## API Testing with Postman

### Import API Collection
1. Download: `auth.collection.json` (provided separately)
2. Postman → Import Collection
3. Select the file

### Test Flow
1. Register endpoint → Get token
2. Use token in Auth header for other requests
3. Test course CRUD operations
4. Test enrollment operations

## Troubleshooting

### Issue: Connection refused (Database)
**Solution:**
```bash
# Start PostgreSQL service
# Windows: Services app → PostgreSQL → Start
# Mac: brew services start postgresql
# Linux: sudo systemctl start postgresql
```

### Issue: Port already in use (Spring Boot)
**Solution:**
```bash
# Find process using port 8080
# Windows: netstat -ano | findstr :8080
# Mac/Linux: lsof -i :8080

# Kill process
# Windows: taskkill /PID <PID> /F
# Mac/Linux: kill -9 <PID>
```

### Issue: npm install fails
**Solution:**
```bash
npm cache clean --force
rm package-lock.json
npm install
```

### Issue: CORS errors
**Solution:**
- Check CORS_ORIGINS in .env
- Verify frontend URL matches
- Restart backend services

## Performance Tips

1. **Increase Java Heap Size:**
   ```bash
   export JAVA_OPTS="-Xmx2048m"
   mvn spring-boot:run
   ```

2. **Build Frontend for Production:**
   ```bash
   cd frontend
   npm run build:production
   # Output: dist/svh-frontend
   ```

3. **Enable database indexing** (already in schema)

## Next Steps

1. Explore the [Architecture Documentation](docs/architecture.md)
2. Read [API Specification](docs/api-spec.md)
3. Follow [Deployment Guide](docs/deployment.md) for production

## Support

For issues and questions:
- Check existing documentation
- Review code comments
- Check service logs
- Consult team members

---

**Last Updated**: May 2026
**Version**: 1.0

