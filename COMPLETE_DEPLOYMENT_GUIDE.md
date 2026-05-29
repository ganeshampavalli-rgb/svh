# Complete Deployment Guide - Frontend + Backend + Database

## 🚀 Deploy Sri Venkateswara Home Tutions to Production

This guide deploys your entire application to free public platforms.

---

## Architecture After Deployment

```
Browser (Worldwide)
    ↓
Vercel CDN (Frontend)
    ↓
Railway API Gateway (Backend)
    ↓
PostgreSQL Database (Neon)
```

---

## Phase 1: Deploy Frontend to Vercel (15 min)

### Step 1: Prepare GitHub

```powershell
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH

git config --global user.name "Your Name"
git config --global user.email "your@email.com"

git add .
git commit -m "Initial deployment - frontend and backend complete"
git remote add origin https://github.com/YOUR_USERNAME/SVH.git
git branch -M main
git push -u origin main
```

### Step 2: Deploy to Vercel

1. Go to: **https://vercel.com/dashboard**
2. Click "New Project"
3. Import GitHub repository "SVH"
4. Configure:
   - **Framework**: Angular
   - **Root Directory**: `frontend`
   - **Build Command**: `npm run build:production`
   - **Output Directory**: `frontend/dist/svh-frontend`
5. Add Environment Variables:
   ```
   NG_APP_API_URL = http://localhost:8080/api
   NG_APP_ENV = production
   ```
6. Click **Deploy**

### Result

```
🎉 Your Frontend is LIVE!
URL: https://svh-frontend.vercel.app
```

---

## Phase 2: Deploy Backend to Railway (30 min)

### Step 1: Create Railway Account

1. Go to: **https://railway.app**
2. Click "Start Project"
3. Select "GitHub"
4. Authorize Railway
5. Select "Deploy from GitHub repo"

### Step 2: Deploy Microservices

You need to deploy 5 backend services. Do this for each:

#### Service 1: Auth Service

1. In Railway:
   - Click "New Project"
   - Select "Deploy from GitHub"
   - Choose your SVH repo
   - Select root: `backend/auth-service`

2. Add Environment Variables:

```
DB_URL=postgresql://[neon-connection]
DB_USERNAME=postgres
DB_PASSWORD=[your-password]
JWT_SECRET=your-secure-key-min-256-bits
CORS_ORIGINS=https://svh-frontend.vercel.app
```

3. Deploy (wait 3-5 min)

4. Get URL: Note down the Railway URL for this service

#### Repeat for Other Services

- Course Service (port 8082)
- Enrollment Service (port 8083)
- Learning Service (port 8084)
- API Gateway (port 8080)

Each takes ~5 minutes.

---

## Phase 3: Setup Database (10 min)

### Create PostgreSQL Database on Neon

1. Go to: **https://neon.tech**
2. Click "Sign Up"
3. Create project: "SVH"
4. Create 4 databases:
   - `svh_auth_db`
   - `svh_course_db`
   - `svh_enrollment_db`
   - `svh_learning_db`

5. Run schema for each database:

```sql
-- Get connection string from Neon
-- Then run this for each database:
psql "postgresql://..." -f database/schema.sql
```

### Get Connection String

From Neon dashboard, copy connection string like:
```
postgresql://user:password@host:port/svh_auth_db
```

---

## Phase 4: Link Everything Together (10 min)

### Update Railway Services with Database URLs

For each Railway service:

1. Go to service settings
2. Add environment variables:

```
DB_URL=postgresql://user:password@neon-host/svh_auth_db
(update database name for each service)
```

### Update Frontend with Backend URLs

In Vercel Dashboard:

1. Go to your project
2. Settings → Environment Variables
3. Update:

```
NG_APP_API_URL=https://your-railway-api-gateway-url/api
```

Vercel auto-redeploys ✅

---

## Phase 5: Get Custom Domain (Optional - 10 min)

### Free Domain Options

**Option 1**: Use Freenom
1. Go to **https://freenom.com**
2. Register free `.tk` domain
3. Get nameservers from Vercel/Railway
4. Update nameservers in Freenom

**Option 2**: Keep Free Subdomains
```
Frontend: svh-frontend.vercel.app
Backend: api-gateway-xxxxx.railway.app
```

---

## Final URLs After Deployment

```
Frontend:      https://svh-frontend.vercel.app
API Gateway:   https://api-gateway-xxxxx.railway.app
Auth Service:  https://auth-service-xxxxx.railway.app
Course API:    https://course-service-xxxxx.railway.app
Enrollment API:https://enrollment-xxxxx.railway.app
Learning API:  https://learning-xxxxx.railway.app
Database:      postgresql://neon-xxxx (not publicly accessible)
```

---

## Test Your Deployment

### Test 1: Frontend Loads
```
Go to: https://svh-frontend.vercel.app
```

### Test 2: API Works
```
curl https://api-gateway-xxxxx.railway.app/api/auth/health
Response: "Auth Service is running"
```

### Test 3: Full Flow (Registration)
1. Open frontend in browser
2. Click "Register"
3. Create account
4. Try to login
5. Should redirect to dashboard

---

## Deployment Checklist

| Component | Platform | Time | Status |
|-----------|----------|------|--------|
| Frontend | Vercel | 15 min | ⏳ Do first |
| Database | Neon | 10 min | ⏳ Do second |
| Auth Service | Railway | 5 min | ⏳ Do third |
| Course Service | Railway | 5 min | ⏳ Do fourth |
| Enrollment Service | Railway | 5 min | ⏳ Do fifth |
| Learning Service | Railway | 5 min | ⏳ Do sixth |
| API Gateway | Railway | 5 min | ⏳ Do seventh |
| Link Everything | Manual | 10 min | ⏳ Do last |

**Total Time**: ~70 minutes (one-time setup)

---

## Auto-Updates

After initial deployment:

**Whenever you push code to GitHub**:
1. ✅ Vercel automatically rebuilds frontend
2. ✅ Railway can be configured to auto-deploy backend
3. ✅ Changes live in 2-5 minutes
4. ✅ No manual redeployment needed!

---

## Cost Analysis

| Service | Cost | Free Tier |
|---------|------|-----------|
| Vercel | $0 | Unlimited builds, hosting |
| Railway | $0 | 5GB storage, Auto-stops after inactivity |
| Neon | $0 | 5GB storage, 3 projects |
| Domain | $0 | .tk from Freenom |

**Total Monthly Cost**: $0 🎉

---

## Common Issues & Solutions

### Build Fails on Vercel
- Check build logs
- Run `npm run build:production` locally
- Fix error and push to GitHub

### API gives CORS error
- Check CORS_ORIGINS in Railway environment variables
- Make sure frontend URL matches exactly (https, no trailing slash)
- Restart services after updating

### Database Connection Fails
- Verify connection string is correct
- Check if Neon database is running
- Verify firewall allows connections

### Services Timeout on Railway
- Railway free tier auto-stops after 30 days inactivity
- Just restart service
- Consider upgrading to paid plan for always-on

---

## Monitoring & Maintenance

### Monitor Deployments
- Vercel: https://vercel.com/dashboard
- Railway: https://railway.app/dashboard
- Neon: https://console.neon.tech

### View Logs
- Vercel: Deployments tab → Click deployment → Logs
- Railway: Service → Logs tab
- Database: Neon console

### Get Alerts
- Setup email notifications in both platforms
- Monitor from dashboards regularly

---

## After Deployment Tasks

1. ✅ Test all functionality
2. ✅ Create test accounts
3. ✅ Test admin features
4. ✅ Monitor performance
5. ✅ Share URLs with users
6. ✅ Collect feedback
7. ✅ Plan future improvements

---

## Scale Up Later

When you need more:
- **Vercel**: Upgrade to Pro ($20/month)
- **Railway**: Upgrade to paid plan (~$5-10/month)
- **Neon**: Upgrade PostgreSQL tier

Or migrate to VPS if needed.

---

## Documentation Files

- ✅ `QUICK_DEPLOY_FRONTEND.md` - Fast frontend deployment
- ✅ `DEPLOY_FRONTEND_VERCEL.md` - Detailed Vercel guide
- ✅ `DEPLOY_FRONTEND_NETLIFY.md` - Netlify alternative
- ✅ `DEPLOY_BACKEND_RAILWAY.md` - Backend deployment (create next)
- ✅ `docs/deployment.md` - Full deployment documentation

---

## Summary

🎉 **After following this guide**:

✅ Frontend live on Vercel
✅ Backend live on Railway
✅ Database on Neon
✅ Everything connected
✅ Your app accessible worldwide
✅ Auto-updates on every GitHub push
✅ FREE forever (no credit card needed)

---

## Status

✅ **READY TO DEPLOY!**

Start with "QUICK_DEPLOY_FRONTEND.md" or "DEPLOY_FRONTEND_VERCEL.md"

Get your frontend live in just 15 minutes!

---

**Last Updated**: May 29, 2026
**Version**: 1.0
**Status**: Production Ready

