# API Specification - Sri Venkateswara Home Tutions

Base URL: `http://localhost:8080/api` (Development)
Production: `https://your-api-domain/api`

All endpoints require JWT token in Authorization header (except login/register):
```
Authorization: Bearer <access_token>
```

## 1. Authentication Service (/auth)

### Register New User
```
POST /auth/register
Content-Type: application/json

Request Body:
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "Password@123",
  "phoneNumber": "9876543210",
  "role": "STUDENT"  // STUDENT, INSTRUCTOR, ADMIN
}

Response (201 Created):
{
  "userId": 1,
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "STUDENT",
  "accessToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "message": "Registration successful"
}

Error (400):
{
  "message": "Email already registered"
}
```

### User Login
```
POST /auth/login
Content-Type: application/json

Request Body:
{
  "email": "john@example.com",
  "password": "Password@123"
}

Response (200 OK):
{
  "userId": 1,
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "STUDENT",
  "accessToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}

Error (401):
{
  "message": "Invalid password"
}
```

### Health Check
```
GET /auth/health

Response (200 OK):
"Auth Service is running"
```

## 2. Course Service (/courses)

### Create Course
```
POST /courses
Authorization: Bearer <token>
Content-Type: application/json

Request Body:
{
  "title": "Java Full Stack Development",
  "description": "Learn full-stack Java development with Spring Boot and Angular",
  "instructorId": 1,
  "level": "PROFESSIONAL",
  "category": "Programming",
  "thumbnailUrl": "https://cdn.example.com/thumb.jpg",
  "price": 0,
  "durationHours": 40
}

Response (201 Created):
{
  "id": 1,
  "title": "Java Full Stack Development",
  "description": "...",
  "instructorId": 1,
  "level": "PROFESSIONAL",
  "category": "Programming",
  "thumbnailUrl": "...",
  "price": 0,
  "isPublished": false,
  "durationHours": 40,
  "rating": null,
  "totalEnrollments": 0
}
```

### Get Course Details
```
GET /courses/{courseId}

Response (200 OK):
{
  "id": 1,
  "title": "Java Full Stack Development",
  ...
}

Error (404):
{
  "message": "Course not found: 1"
}
```

### Update Course
```
PUT /courses/{courseId}
Authorization: Bearer <token>
Content-Type: application/json

Request Body:
{
  "title": "Advanced Java Full Stack",
  "isPublished": true
}

Response (200 OK):
{
  "id": 1,
  "title": "Advanced Java Full Stack",
  ...
}
```

### Delete Course
```
DELETE /courses/{courseId}
Authorization: Bearer <token>

Response (204 No Content)
```

### Get Published Courses
```
GET /courses/published?page=0&size=10

Response (200 OK):
{
  "content": [
    { "id": 1, "title": "...", ... },
    { "id": 2, "title": "...", ... }
  ],
  "totalElements": 5,
  "totalPages": 1
}
```

### Get Courses by Category
```
GET /courses/category/Programming?page=0&size=10

Response (200 OK):
{
  "content": [...],
  "totalElements": 3,
  "totalPages": 1
}
```

### Get Courses by Level
```
GET /courses/level/PROFESSIONAL?page=0&size=10

Response (200 OK):
{
  "content": [...],
  "totalElements": 2,
  "totalPages": 1
}
```

### Get Instructor's Courses
```
GET /courses/instructor/{instructorId}

Response (200 OK):
[
  { "id": 1, "title": "...", ... },
  { "id": 2, "title": "...", ... }
]
```

### Health Check
```
GET /courses/health

Response (200 OK):
"Course Service is running"
```

## 3. Enrollment Service (/enrollments)

### Enroll in Course
```
POST /enrollments
Authorization: Bearer <token>
Content-Type: application/json

Request Body:
{
  "studentId": 1,
  "courseId": 1
}

Response (201 Created):
{
  "id": 1,
  "studentId": 1,
  "courseId": 1,
  "status": "ACTIVE",
  "completionPercentage": 0
}

Error (409):
{
  "message": "Already enrolled in this course"
}
```

### Get Student Enrollments
```
GET /enrollments/student/{studentId}
Authorization: Bearer <token>

Response (200 OK):
[
  {
    "id": 1,
    "studentId": 1,
    "courseId": 1,
    "status": "ACTIVE",
    "completionPercentage": 25
  },
  ...
]
```

### Get Course Enrollments
```
GET /enrollments/course/{courseId}
Authorization: Bearer <token>

Response (200 OK):
[
  {
    "id": 1,
    "studentId": 1,
    "courseId": 1,
    "status": "ACTIVE",
    "completionPercentage": 25
  },
  ...
]
```

### Update Enrollment Status
```
PUT /enrollments/{enrollmentId}
Authorization: Bearer <token>
Content-Type: application/json

Request Body:
{
  "status": "COMPLETED",
  "completionPercentage": 100
}

Response (200 OK):
{
  "id": 1,
  "studentId": 1,
  "courseId": 1,
  "status": "COMPLETED",
  "completionPercentage": 100
}
```

### Health Check
```
GET /enrollments/health

Response (200 OK):
"Enrollment Service is running"
```

## 4. Learning Service (/learning)

### Get Student Progress
```
GET /learning/progress/{enrollmentId}
Authorization: Bearer <token>

Response (200 OK):
{
  "message": "Progress tracking endpoint",
  "progress": {
    "enrollmentId": 1,
    "lessonsCompleted": 5,
    "totalLessons": 20,
    "percentage": 25
  }
}
```

### Update Student Progress
```
POST /learning/progress/{enrollmentId}/update
Authorization: Bearer <token>
Content-Type: application/json

Request Body:
{
  "lessonId": 1,
  "watchedDuration": 45,
  "isCompleted": true
}

Response (200 OK):
{
  "message": "Progress updated"
}
```

### Get Assignments
```
GET /learning/assignments/{lessonId}
Authorization: Bearer <token>

Response (200 OK):
{
  "message": "Assignments endpoint",
  "assignments": [
    {
      "id": 1,
      "title": "Assignment 1",
      "description": "...",
      "dueDate": "2026-06-15T00:00:00"
    }
  ]
}
```

### Submit Assignment
```
POST /learning/assignments/{assignmentId}/submit
Authorization: Bearer <token>
Content-Type: multipart/form-data

Request Body:
- submissionText: "My solution for the assignment"
- file: (binary file content)

Response (200 OK):
{
  "message": "Assignment submitted",
  "submissionId": 1,
  "status": "SUBMITTED"
}
```

### Health Check
```
GET /learning/health

Response (200 OK):
"Learning Service is running"
```

## Error Handling

All errors follow standard HTTP status codes:

```
200 OK              - Request successful
201 Created         - Resource created
204 No Content      - Successful deletion/update
400 Bad Request     - Invalid request format
401 Unauthorized    - Missing/invalid token
403 Forbidden       - Insufficient permissions
404 Not Found       - Resource not found
409 Conflict        - Resource conflict (e.g., duplicate)
500 Internal Error  - Server error
```

## Rate Limiting

Future implementation:
- 100 requests per minute per user
- 1000 requests per minute per IP

## Pagination

All list endpoints support pagination:
```
?page=0&size=10&sort=createdAt,desc

Response includes:
- content: array of items
- totalElements: total count
- totalPages: number of pages
- currentPage: current page number
```

## Authentication Token Format

Access Token (JWT):
```
{
  "sub": "john@example.com",
  "userId": 1,
  "role": "STUDENT",
  "iat": 1685000000,
  "exp": 1685086400
}
```

Refresh Token (JWT):
```
{
  "sub": "john@example.com",
  "userId": 1,
  "type": "refresh",
  "iat": 1685000000,
  "exp": 1685604800
}
```

## API Response Wrapper

Success Response:
```json
{
  "success": true,
  "statusCode": 200,
  "data": {...},
  "message": "Operation successful",
  "timestamp": "2026-05-29T10:23:45"
}
```

Error Response:
```json
{
  "success": false,
  "statusCode": 400,
  "data": null,
  "message": "Validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email already exists"
    }
  ],
  "timestamp": "2026-05-29T10:23:45"
}
```

---

**API Version**: 1.0
**Last Updated**: May 2026

