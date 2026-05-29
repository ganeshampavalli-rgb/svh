# Architecture Documentation - Sri Venkateswara Home Tutions

## System Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Layer                            │
│                    Angular Frontend (Vercel)                    │
└────────────────┬────────────────────────────────────────────────┘
                 │ HTTP/HTTPS
┌────────────────▼────────────────────────────────────────────────┐
│                    API Gateway Layer                            │
│              (Spring Cloud Gateway - Railway)                   │
│  - Request Routing                                              │
│  - Authentication/Authorization                                │
│  - API Rate Limiting                                            │
└────┬────────┬────────────┬──────────────┬──────────────────────┘
     │        │            │              │
┌────▼──┐ ┌──▼───┐ ┌──────▼─┐ ┌─────────▼──┐
│ Auth  │ │Course│ │Enroll  │ │ Learning   │
│Service│ │Service│ │Service │ │ Service    │
│ :8081 │ │ :8082 │ │ :8083  │ │  :8084     │
└────┬──┘ └──┬───┘ └───┬────┘ └──────┬─────┘
     │       │         │             │
     └───────┴─────────┴─────────────┘
              │
┌─────────────▼──────────────────────────────────────────────────┐
│                    PostgreSQL Database                          │
│                    (Neon - Free Tier)                          │
│  - svh_auth_db (User Management)                              │
│  - svh_course_db (Course Management)                          │
│  - svh_enrollment_db (Enrollment Tracking)                    │
│  - svh_learning_db (Progress & Materials)                     │
└────────────────────────────────────────────────────────────────┘
```

## Microservices Architecture

### 1. API Gateway Service (Port 8080)
**Responsibilities:**
- Route requests to appropriate microservices
- JWT token validation and refresh
- CORS handling
- API versioning support
- Rate limiting (future)

**Dependencies:**
- Spring Cloud Gateway
- Spring Security
- JWT (JJWT)

### 2. Auth Service (Port 8081)
**Responsibilities:**
- User registration and login
- JWT token generation
- Password encryption and validation
- User profile management
- Email verification (future)

**Entities:**
- `User` - User account information

**API Endpoints:**
```
POST   /api/auth/register    - Register new user
POST   /api/auth/login       - User login
GET    /api/auth/health      - Service health check
```

### 3. Course Service (Port 8082)
**Responsibilities:**
- Course creation and management
- Course catalog browsing
- Course filtering by level, category
- Course availability management

**Entities:**
- `Course` - Course information
- `CourseModule` - Course chapters/sections
- `Lesson` - Individual lessons
- `LearningMaterial` - Downloadable materials
- `Quiz` - Course assessments

**API Endpoints:**
```
POST   /api/courses          - Create course
GET    /api/courses/{id}     - Get course details
PUT    /api/courses/{id}     - Update course
DELETE /api/courses/{id}     - Delete course
GET    /api/courses/published - Get published courses
GET    /api/courses/category/{cat} - Filter by category
GET    /api/courses/level/{level}  - Filter by level
GET    /api/courses/instructor/{id} - Get instructor courses
```

### 4. Enrollment Service (Port 8083)
**Responsibilities:**
- Manage student course enrollments
- Track enrollment status
- Handle enrollment cancellations
- Calculate completion percentage

**Entities:**
- `Enrollment` - Student course enrollment

**API Endpoints:**
```
POST   /api/enrollments                 - Enroll in course
GET    /api/enrollments/student/{id}    - Get student enrollments
GET    /api/enrollments/course/{id}     - Get course enrollments
PUT    /api/enrollments/{id}            - Update enrollment status
```

### 5. Learning Service (Port 8084)
**Responsibilities:**
- Track student progress
- Manage assignments and submissions
- Handle quiz attempts and grading
- Provide learning analytics

**Entities:**
- `StudentProgress` - Lesson completion tracking
- `Assignment` - Assignment details
- `Submission` - Student submissions
- `QuizAttempt` - Quiz attempt records
- `Certificate` - Course completion certificates

**API Endpoints:**
```
GET    /api/learning/progress/{enrollmentId}         - Get progress
POST   /api/learning/progress/{enrollmentId}/update  - Update progress
GET    /api/learning/assignments/{lessonId}         - Get assignments
POST   /api/learning/assignments/{assignmentId}/submit - Submit
```

## Data Model

### User Roles
- **STUDENT** - Course participant
- **INSTRUCTOR** - Course creator/teacher
- **ADMIN** - Platform administrator

### Course Levels
- NURSERY (Play school)
- PRIMARY (1-5)
- SECONDARY (6-10)
- JNTU (11-12)
- GRADUATION (B.Tech)
- PROFESSIONAL (Java, AWS, Angular)

### Enrollment Status
- ACTIVE - Currently enrolled
- COMPLETED - Course finished
- DROPPED - Dropped by student
- PAUSED - Temporarily paused

### Assignment Status
- PENDING - Not started
- SUBMITTED - Student submitted
- GRADED - Instructor graded
- RESUBMIT - Resubmission requested

## Technology Stack

### Backend
| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Spring Boot | 3.2.0 |
| Language | Java | 17 |
| API Gateway | Spring Cloud Gateway | 2023.0.0 |
| Database | PostgreSQL | 15+ |
| Security | Spring Security + JWT | JJWT 0.12.3 |
| ORM | Hibernate/JPA | Latest |
| Build Tool | Maven | 3.9+ |
| Container | Docker (optional) | 20.10+ |

### Frontend
| Component | Technology | Version |
|-----------|-----------|---------|
| Framework | Angular | 17.0.0 |
| Language | TypeScript | 5.2.0 |
| State | RxJS | 7.8.0 |
| Styling | SCSS/Tailwind | 3.3.0 |
| HTTP Client | Angular HttpClient | 17.0.0 |
| Package Manager | npm | 10+ |

### Database
| Component | Technology |
|-----------|-----------|
| DBMS | PostgreSQL |
| Connection Pool | HikariCP |
| Migrations | Flyway (optional) |

### Hosting
| Component | Platform |
|-----------|----------|
| Backend | Railway.app / Render.com |
| Frontend | Vercel / Netlify |
| Database | Neon.tech |
| Storage | Cloudinary |
| VCS | GitHub |

## API Communication Flow

```
1. User Registration
   ┌─────────┐         ┌──────────┐         ┌──────────┐
   │ Browser │ POST    │   API    │         │   Auth   │
   │ Angular │─────→   │ Gateway  │─────→   │ Service  │
   └─────────┘  /auth/ │          │         │          │
               register└──────────┘         └────┬─────┘
                                                  │
                                           ┌────▼──────┐
                                           │ PostgreSQL │
                                           │ (User DB)  │
                                           └────────────┘

2. Course Browsing
   ┌─────────┐         ┌──────────┐         ┌────────────┐
   │ Browser │ GET     │   API    │         │  Course    │
   │ Angular │─────→   │ Gateway  │─────→   │  Service   │
   └─────────┘  /courses└──────────┘         └──────────┘
                                                   │
                                           ┌────▼──────────┐
                                           │ PostgreSQL     │
                                           │(Course DB)     │
                                           └────────────────┘

3. Course Enrollment
   ┌─────────┐         ┌──────────┐         ┌────────────┐
   │ Browser │ POST    │   API    │         │ Enrollment │
   │ Angular │─────→   │ Gateway  │─────→   │  Service   │
   └─────────┘ /enrollments └────────────┘   └──────────┘
                                                   │
                                           ┌────▼──────────┐
                                           │ PostgreSQL     │
                                           │(Enrollment DB) │
                                           └────────────────┘
```

## Security Architecture

### Authentication Flow
```
1. User provides credentials
   ↓
2. Auth Service validates and generates JWT
   ├─ Access Token (15 min expiry)
   └─ Refresh Token (7 days expiry)
   ↓
3. Frontend stores tokens in localStorage
   ↓
4. Auth Interceptor adds token to each request
   ↓
5. API Gateway validates token
   ├─ Valid → Request proceeds
   └─ Invalid → 401 Unauthorized response
```

### Password Security
- Hashed using BCrypt (Spring Security)
- Minimum 8 characters required
- Pepper/salt automatically handled by BCrypt

### CORS Policy
```
Allowed Origins:
- http://localhost:4200 (development)
- https://your-frontend-domain.com (production)

Allowed Methods:
- GET, POST, PUT, DELETE, OPTIONS

Allowed Headers:
- Authorization, Content-Type, Accept
```

## Database Schema Relationships

```
Users (1) ──────────── (Many) Courses (as Instructor)
  │
  ├─ (1) ──────────── (Many) Enrollments
  │                      │
  │                      └─ (Many) Student Progress
  │                      │
  │                      └─ (Many) Submissions
  │                      │
  │                      └─ (Many) Quiz Attempts
  │
  └─ (1) ──────────── (Many) Certificates

Courses (1) ──────────── (Many) Modules
  │                         │
  ├─ (Many) ───────────────┘
  │
  ├─ (1) ──────────── (Many) Lessons
  │                      │
  │                      ├─ (Many) Learning Materials
  │                      │
  │                      ├─ (Many) Quizzes
  │                      │         │
  │                      │         └─ (Many) Questions
  │                      │             │
  │                      │             └─ (Many) Answers
  │                      │
  │                      └─ (Many) Assignments
  │
  └─ (Many) Enrollments
```

## Deployment Architecture

### Development Environment
```
localhost:4200  → Angular Dev Server
localhost:8080  → API Gateway
localhost:8081  → Auth Service
localhost:8082  → Course Service
localhost:8083  → Enrollment Service
localhost:8084  → Learning Service
localhost:5432  → PostgreSQL
```

### Production Environment
```
api.yourdomain.com      → API Gateway (Railway)
app.yourdomain.com      → Angular Frontend (Vercel)
postgres-server.neon.tech → PostgreSQL (Neon)
cdn.cloudinary.com      → Media Files (Cloudinary)
```

## Performance Optimization

### Database
- Indexed columns: email, instructor_id, course_id, student_id
- Query optimization with JPA fetch strategies
- Connection pooling (HikariCP: 10 connections)

### Backend
- Response compression (Gzip)
- API pagination (page size: 10)
- Caching headers for static content

### Frontend
- Lazy loading of modules
- Tree shaking in production build
- Image optimization
- OnPush change detection

## Monitoring & Logging

### Logging Levels
```
ERROR   - Critical failures
WARNING - Potential issues
INFO    - Important events
DEBUG   - Detailed debugging info (dev only)
```

### Health Checks
```
GET /api/auth/health        → Auth Service status
GET /api/courses/health     → Course Service status
GET /api/enrollments/health → Enrollment Service status
GET /api/learning/health    → Learning Service status
```

## Future Enhancements

1. **Phase 2**
   - Real-time notifications (WebSockets)
   - Discussion forums
   - Student peer-to-peer messaging
   - Video conferencing (Zoom integration)

2. **Phase 3**
   - AI-powered course recommendations
   - Automated grading for MCQs
   - Student analytics dashboard
   - Payment integration (Razorpay)

3. **Phase 4**
   - Mobile app (Flutter)
   - Offline learning support
   - Blockchain certificates
   - Multi-language support

---

**Architecture Version**: 1.0
**Last Updated**: May 2026

