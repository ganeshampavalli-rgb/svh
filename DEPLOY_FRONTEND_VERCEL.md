# Deploy Angular Frontend to Vercel (FREE) - Step by Step

## Option 1: Deploy to Vercel (EASIEST - Recommended)

Vercel is the easiest option. It automatically builds and deploys Angular apps. You get a free public URL instantly.

### Prerequisites
- GitHub account (free)
- Vercel account (free)
- Your code pushed to GitHub

---

## Step 1: Push Your Code to GitHub

### If you haven't already:

```bash
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH

git config --global user.name "Your Name"
git config --global user.email "your.email@gmail.com"

git add .
git commit -m "SVH Platform - Initial commit with complete frontend and backend"
git remote add origin https://github.com/YOUR_USERNAME/SVH.git
git branch -M main
git push -u origin main
```

**Replace**:
- `YOUR_USERNAME` with your GitHub username
- `Your Name` with your name
- `your.email@gmail.com` with your email

### If GitHub repo already exists:
```bash
cd C:\Users\GaneshAmpavalli\Ganesh_Workspace\SVH
git add .
git commit -m "Frontend and backend complete"
git push
```

---

## Step 2: Create Vercel Account (5 minutes)

1. Go to: **https://vercel.com**
2. Click "Sign Up"
3. Choose "GitHub" to sign up with GitHub
4. Authorize Vercel to access your GitHub account
5. You're done!

---

## Step 3: Deploy to Vercel (1 minute)

### Option A: Deploy via Vercel Dashboard

1. Go to: **https://dashboard.vercel.com**
2. Click "New Project"
3. Select your **"SVH"** repository
4. Configure build settings (see below)
5. Click "Deploy"

### Option B: Select Repository

When you create new project in Vercel:

**Project Settings**:
- **Framework**: Angular
- **Root Directory**: `frontend`
- **Build Command**: `npm run build:production`
- **Output Directory**: `dist/svh-frontend`

---

## Step 4: Configure Environment Variables

In Vercel Dashboard for your project:

1. Go to **Settings** → **Environment Variables**
2. Add these variables:

```
NG_APP_API_URL=https://your-backend-api-url.com
NG_APP_ENV=production
```

**For now**, you can use:
```
NG_APP_API_URL=http://localhost:8080/api
```

(You can update this later when backend is deployed)

---

## Step 5: Deploy Complete! 🎉

After clicking deploy, Vercel will:

1. ✅ Clone your GitHub repo
2. ✅ Build the Angular app
3. ✅ Deploy to CDN
4. ✅ Give you a live URL (something like: `svh-frontend.vercel.app`)

**Your app is now PUBLIC!** 🌍

---

## Step 6: Access Your Live App

After deployment completes:

1. Go to: **https://YOUR_PROJECT_NAME.vercel.app**
2. You should see your SVH home page
3. Share this URL with anyone

---

## Get Your Custom Domain (FREE)

### Option 1: Free Vercel Subdomain
You already have: `svh-frontend.vercel.app` (Free!)

### Option 2: Free Custom Domain (from Freenom)

1. Go to: **https://www.freenom.com**
2. Search for a domain (e.g., `myhometutions.tk`)
3. Register for free
4. Get the domain nameservers
5. In Vercel Settings → Domains, add your custom domain
6. Update nameservers to Vercel's

---

## Verify Deployment

### Check 1: App Loads
```
https://svh-frontend.vercel.app (or your domain)
Should show SVH home page
```

### Check 2: Navigation Works
- Click "Register" button
- Click "Login" button
- All buttons work

### Check 3: API Connection (Will fail for now)
- Try to register - will fail because backend is local
- This is expected until backend is deployed

---

## Update Backend API URL (Later)

Once your backend is deployed to Railway/Render:

1. In Vercel Dashboard → Settings → Environment Variables
2. Update `NG_APP_API_URL` to your deployed backend URL
3. Vercel automatically rebuilds and redeploys

---

## Automatic Updates

**Whenever you push to GitHub**, Vercel automatically:
1. Detects new commits
2. Rebuilds the application
3. Deploys to production
4. Within 1-2 minutes, your changes are live!

No manual deployment needed after first setup 🎉

---

## Troubleshooting

### Build Failed
**Check**: Vercel logs → Settings → Deployments
Look for error messages and fix locally

### Page Not Loading
**Check**: Browser console (F12) for errors
Verify API URL in Environment Variables

### CORS Errors
This happens when backend isn't deployed yet
Will be fixed when you deploy backend API

---

## Summary

| Step | Time | Status |
|------|------|--------|
| Push to GitHub | 2 min | ✅ Do this |
| Create Vercel Account | 5 min | ✅ Do this |
| Deploy to Vercel | 1 min | ✅ Do this |
| Wait for Build | 3-5 min | ⏳ Automatic |
| Access Live URL | Instant | 🎉 Done! |

**Total Time**: ~15-20 minutes

---

## Your Public URL

Once deployed, your app will be at:

```
https://svh-frontend.vercel.app
(or your custom domain if you added one)
```

**Share this URL with anyone! They can access your app from anywhere in the world!** 🌍

---

## What's Next

1. ✅ Frontend deployed to Vercel
2. ⏳ Deploy backend to Railway.app
3. ⏳ Update API URL in Vercel
4. ⏳ Test full application
5. ⏳ Add custom domain (optional)

---

**Last Updated**: May 29, 2026
**Platform**: Vercel (Free Tier)
**Status**: Ready to Deploy

