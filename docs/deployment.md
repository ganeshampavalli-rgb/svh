# Deployment Guide - Sri Venkateswara Home Tutions

This guide explains how to deploy the SVH application on free cloud platforms.

## Free Platforms Used

### Backend Services
- **Railway.app** - Backend microservices deployment (free tier: 500 hours/month)
- **Render.com** - Alternative for backend services
- **Neon.tech** - PostgreSQL Database (free tier: 5GB, 3 projects)

### Frontend
- **Vercel** - Angular frontend hosting (free tier)
- **Netlify** - Alternative frontend hosting

### Additional Services
- **GitHub** - Source code repository (free)
- **Cloudinary** - Image & file storage (free tier: 25GB/month)

## Prerequisites

- Git installed
- GitHub account
- Node.js 18+ installed
- Java 17+ installed
- Docker (optional, for local testing)

## Step 1: Setup GitHub Repository

```bash
# Initialize git repo (if not already done)
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"

# Add all files
git add .

# Commit
git commit -m "Initial commit: SVH platform setup"

# Create repository on GitHub and push
git remote add origin https://github.com/your-username/SVH.git
git branch -M main
git push -u origin main
```

## Step 2: Setup Database on Neon

1. Go to https://neon.tech
2. Sign up with GitHub
3. Create a new project:
   - Project name: `svh-db`
   - Region: Choose closest to your location
4. Copy the connection string
5. Create databases for each service:
   ```sql
   CREATE DATABASE svh_auth_db;
   CREATE DATABASE svh_course_db;
   CREATE DATABASE svh_enrollment_db;
   CREATE DATABASE svh_learning_db;
   ```
6. Run the schema from `database/schema.sql` against each database

## Step 3: Deploy Backend on Railway.app

### Auth Service Deployment

1. Go to https://railway.app
2. Sign in with GitHub
3. Create new project → GitHub Repo
4. Select your SVH repository
5. Configure environment variables:
   ```
   DB_URL=postgresql://user:password@host:port/svh_auth_db
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   JWT_SECRET=your-very-secure-secret-key-min-256-bits
   JWT_EXPIRATION=86400
   CORS_ORIGINS=http://localhost:4200,https://your-frontend-domain.com
   ```
6. Add deployment config in `railway.json`:
   ```json
   {
     "buildCommand": "cd backend/auth-service && mvn clean install -DskipTests",
     "startCommand": "cd backend/auth-service && java -jar target/svh-auth-service-1.0.0.jar"
   }
   ```

### Repeat for Other Services

- Course Service (auth-service → course-service → 8082)
- Enrollment Service (→ enrollment-service → 8083)
- Learning Service (→ learning-service → 8084)
- API Gateway (→ api-gateway → 8080)

## Step 4: Deploy Frontend on Vercel

1. Go to https://vercel.com
2. Sign in with GitHub
3. Click "New Project"
4. Import your SVH repository
5. Framework: Angular
6. Configure environment variables:
   ```
   NG_APP_API_URL=https://your-api-gateway-url
   ```
7. Build settings:
   - Build Command: `npm run build:production` (from frontend folder)
   - Output Directory: `frontend/dist/svh-frontend`
8. Deploy

## Step 5: Alternative - Deploy on Render.com

1. Go to https://render.com
2. Create new Web Service
3. Connect GitHub repository
4. Configuration:
   - Build Command: `cd backend/auth-service && mvn clean install`
   - Start Command: `java -jar target/svh-auth-service-1.0.0.jar`
5. Add environment variables from step 3
6. Deploy

## Step 6: Setup Custom Domain (Optional)

### Free Domain Options:
- **Freenom** - Free .tk, .ml, .ga domains
- **NoIP** - Dynamic DNS (free)
- Railway and Vercel provide free subdomains

### Connect Custom Domain:

**On Railway:**
```bash
# Add custom domain in Railway dashboard
Domain: api.yourdomain.com
```

**On Vercel:**
```bash
# Add custom domain in Vercel dashboard
Domain: app.yourdomain.com
```

## Step 7: Environment Configuration Files

Create `.env` files in each service:

### backend/auth-service/.env
```
DB_URL=postgresql://neon-host/svh_auth_db
DB_USERNAME=user
DB_PASSWORD=password
JWT_SECRET=your-secure-key
EUREKA_URL=http://your-eureka-url:8761/eureka/
CORS_ORIGINS=https://your-frontend.com
```

### frontend/.env
```
NG_APP_API_URL=https://your-api-gateway-url
NG_APP_ENV=production
```

## Step 8: Local Development Setup

### Backend Development

```bash
# Install PostgreSQL Locallyand create databases:
# svh_auth_db, svh_course_db, svh_enrollment_db, svh_learning_db

# Run Auth Service
cd backend/auth-service
mvn spring-boot:run

# Run Course Service (new terminal)
cd backend/course-service
mvn spring-boot:run

# And so on for other services...
```

### Frontend Development

```bash
cd frontend
npm install
npm start
# Navigate to http://localhost:4200
```

## Step 9: Monitoring and Logging

- Railway/Render: Built-in logs available in dashboard
- Check logs: `railway logs` or `render logs`
- Monitor endpoints at `/api/*/health`

## Database Backup Strategy

1. Use Neon's built-in backup (daily automatic backups)
2. Export periodically:
   ```bash
   pg_dump postgresql://user:pass@host/svh_auth_db > backup.sql
   ```

## CI/CD Pipeline (GitHub Actions)

Create `.github/workflows/deploy.yml`:

```yaml
name: Deploy SVH

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Deploy to Railway
        run: |
          npm install -g @railway/cli
          railway link ${{ secrets.RAILWAY_PROJECT_ID }}
          railway up
```

## Troubleshooting

### Database Connection Issues
```bash
# Test connection
psql postgresql://user:password@host:port/dbname
```

### Microservices Communication
- Ensure all services are running
- Check network policies in Railway/Render
- Verify environment variables are set

### Frontend API Issues
- Check CORS settings in backend
- Verify API URL in environment
- Check browser console for errors

## Scaling (Phase 2)

1. Add Redis for caching
2. Implement message queues (RabbitMQ)
3. Use CDN for static assets (Cloudinary)
4. Add API rate limiting
5. Implement load balancing

## Cost Optimization

- **Total Monthly Cost: ~$0** (all free tiers)
- Railway: 500 free hours/month (sufficient for dev/staging)
- Neon: Free PostgreSQL with 5GB storage
- Vercel: Free serverless functions and hosting
- GitHub: Free private repositories
- Cloudinary: 25GB free bandwidth per month

## Support & Resources

- Railway Docs: https://docs.railway.app
- Neon Docs: https://neon.tech/docs
- Vercel Docs: https://vercel.com/docs
- Spring Boot: https://spring.io/projects/spring-boot
- Angular: https://angular.io/docs

---

**Last Updated**: May 2026
**Version**: 1.0

