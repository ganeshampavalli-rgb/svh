# Sri Venkateswara Home Tutions 🎓

An innovative online tutoring platform for students from Nursery to B.Tech with specialized courses in Java Full Stack, AWS, and Angular.

## Project Overview

This is a full-stack application built with:
- **Backend**: Java Spring Boot Microservices with REST APIs
- **Frontend**: Angular (responsive design)
- **Database**: PostgreSQL
- **Hosting**: Free tier services (Railway/Render, Vercel/Netlify)

## Project Structure

```
SVH/
├── backend/                 # Spring Boot Microservices
│   ├── api-gateway/        # API Gateway (routing, auth)
│   ├── auth-service/       # User authentication & management
│   ├── course-service/     # Course management & catalog
│   ├── enrollment-service/ # Student enrollments
│   └── learning-service/   # Learning materials, videos, progress
├── frontend/               # Angular Application
√   ├── src/
│   ├── angular.json
│   └── package.json
├── database/               # Database schema & migrations
│   ├── schema.sql
│   └── seed-data.sql
├── docs/                   # Documentation
│   ├── architecture.md
│   ├── api-spec.md
│   └── deployment.md
└── README.md
```

## Features 🚀

### For Students
- Browse and enroll in courses (Nursery to B.Tech)
- Watch video lectures and access materials
- Track learning progress
- Submit assignments and get feedback
- Interactive quizzes and assessments

### For Instructors
- Create and manage courses
- Upload video content and materials
- Grade assignments
- View student progress analytics

### For Admins
- Manage users and roles
- Monitor platform analytics
- Manage course catalog

## Tech Stack Details

### Backend
- Java 17+
- Spring Boot 3.x
- Spring Cloud (for microservices)
- Spring Data JPA + Hibernate
- Spring Security with JWT
- Spring Cloud Gateway
- Maven

### Frontend
- Angular 17+
- Angular Material
- RxJS
- TypeScript
- Tailwind CSS

### Database
- PostgreSQL (Neon or Supabase free tier)

### Hosting
- **Backend**: Railway.app or Render.com
- **Frontend**: Vercel or Netlify
- **Database**: Neon or Supabase
- **Storage**: Cloudinary (for course materials)

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- npm or yarn
- PostgreSQL (local development)
- Git

### Installation

#### Backend Setup
```bash
cd backend/api-gateway
mvn clean install
mvn spring-boot:run
```

#### Frontend Setup
```bash
cd frontend
npm install
ng serve
```

## Deployment

See [deployment.md](docs/deployment.md) for detailed deployment instructions on free platforms.

## API Documentation

See [api-spec.md](docs/api-spec.md) for complete API specifications.

## Contributing

Please read our contribution guidelines before submitting pull requests.

## License

This project is licensed under the MIT License.

## Contact

For inquiries about Sri Venkateswara Home Tutions, please contact us through the platform.

---

**Building Quality Education, One Course at a Time** 📚

