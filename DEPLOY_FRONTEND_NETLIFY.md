# Deploy Angular Frontend to Netlify (FREE) - Alternative Option

## Option 2: Deploy to Netlify (Also Excellent)

If you prefer Netlify over Vercel, follow this guide.

### Prerequisites
- GitHub account
- Netlify account
- Code pushed to GitHub

---

## Step 1: Create Netlify Account

1. Go to: **https://www.netlify.com**
2. Click "Sign up"
3. Choose "Sign up with GitHub"
4. Authorize Netlify to access GitHub
5. Done!

---

## Step 2: Deploy to Netlify

### From Netlify Dashboard:

1. Click "New site from Git"
2. Select "GitHub"
3. Find and select **"SVH"** repository
4. Click "Deploy site"

---

## Step 3: Configure Build Settings

When Netlify asks for build settings:

**Build Settings**:
- **Base directory**: `frontend`
- **Build command**: `npm run build:production`
- **Publish directory**: `frontend/dist/svh-frontend`

---

## Step 4: Add Environment Variables

In Netlify:

1. Go to **Site settings** → **Build & deploy** → **Environment**
2. Click **Edit variables**
3. Add:

```
NG_APP_API_URL = http://localhost:8080/api
NG_APP_ENV = production
```

---

## Step 5: Auto-Deploy is Enabled!

Netlify automatically redeploys whenever you push to GitHub.

---

## Get Your Netlify URL

After deployment:

```
https://xxxxx.netlify.app
```

This is your public URL! Share it anywhere 🌍

---

## Connect Custom Domain (Optional)

1. Buy domain from Freenom or other registrar
2. In Netlify → Site settings → Domain settings
3. Add custom domain
4. Update nameservers

---

## Key Differences from Vercel

| Feature | Vercel | Netlify |
|---------|--------|---------|
| Speed | Slightly faster | Very fast |
| Setup | Very easy | Very easy |
| Free Tier | Excellent | Excellent |
| Analytics | Built-in | Add-on |
| Custom Domain | Free | Free |

Both are excellent choices!

---

**Choose one and deploy your frontend today!**

