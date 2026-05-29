-- Database Schema for Sri Venkateswara Home Tutions
-- PostgreSQL

-- Create ENUM types
CREATE TYPE user_role AS ENUM ('STUDENT', 'INSTRUCTOR', 'ADMIN');
CREATE TYPE course_level AS ENUM ('NURSERY', 'PRIMARY', 'SECONDARY', 'JNTU', 'GRADUATION', 'PROFESSIONAL');
CREATE TYPE assignment_status AS ENUM ('PENDING', 'SUBMITTED', 'GRADED', 'RESUBMIT');
CREATE TYPE enrollment_status AS ENUM ('ACTIVE', 'COMPLETED', 'DROPPED', 'PAUSED');

-- Users Table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    role user_role NOT NULL DEFAULT 'STUDENT',
    profile_picture_url VARCHAR(500),
    bio TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

-- Courses Table
CREATE TABLE courses (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    instructor_id INTEGER NOT NULL REFERENCES users(id) ON DELETE SET NULL,
    level course_level NOT NULL,
    category VARCHAR(100) NOT NULL,
    thumbnail_url VARCHAR(500),
    cover_image_url VARCHAR(500),
    price DECIMAL(10, 2) DEFAULT 0,
    is_published BOOLEAN DEFAULT FALSE,
    duration_hours INTEGER,
    rating DECIMAL(3, 2),
    total_enrollments INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Course Modules (Chapters)
CREATE TABLE course_modules (
    id SERIAL PRIMARY KEY,
    course_id INTEGER NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    order_index INTEGER NOT NULL,
    duration_minutes INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(course_id, order_index)
);

-- Lessons Table
CREATE TABLE lessons (
    id SERIAL PRIMARY KEY,
    module_id INTEGER NOT NULL REFERENCES course_modules(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    video_url VARCHAR(500),
    video_duration_minutes INTEGER,
    lesson_content TEXT,
    order_index INTEGER NOT NULL,
    is_published BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(module_id, order_index)
);

-- Learning Materials
CREATE TABLE learning_materials (
    id SERIAL PRIMARY KEY,
    lesson_id INTEGER NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    file_url VARCHAR(500) NOT NULL,
    file_type VARCHAR(20),
    file_size_kb INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quizzes Table
CREATE TABLE quizzes (
    id SERIAL PRIMARY KEY,
    lesson_id INTEGER NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    total_questions INTEGER NOT NULL,
    pass_percentage DECIMAL(5, 2) DEFAULT 60,
    is_published BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quiz Questions
CREATE TABLE quiz_questions (
    id SERIAL PRIMARY KEY,
    quiz_id INTEGER NOT NULL REFERENCES quizzes(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    question_type VARCHAR(20),
    marks INTEGER DEFAULT 1,
    order_index INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Quiz Answers (Options)
CREATE TABLE quiz_answers (
    id SERIAL PRIMARY KEY,
    question_id INTEGER NOT NULL REFERENCES quiz_questions(id) ON DELETE CASCADE,
    answer_text TEXT NOT NULL,
    is_correct BOOLEAN DEFAULT FALSE,
    order_index INTEGER
);

-- Enrollments Table
CREATE TABLE enrollments (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    course_id INTEGER NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status enrollment_status DEFAULT 'ACTIVE',
    completion_percentage INTEGER DEFAULT 0,
    last_accessed TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(student_id, course_id)
);

-- Student Progress Tracking
CREATE TABLE student_progress (
    id SERIAL PRIMARY KEY,
    enrollment_id INTEGER NOT NULL REFERENCES enrollments(id) ON DELETE CASCADE,
    lesson_id INTEGER NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    is_completed BOOLEAN DEFAULT FALSE,
    watched_duration_minutes INTEGER DEFAULT 0,
    completed_at TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(enrollment_id, lesson_id)
);

-- Assignments Table
CREATE TABLE assignments (
    id SERIAL PRIMARY KEY,
    lesson_id INTEGER NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date TIMESTAMP NOT NULL,
    max_score INTEGER DEFAULT 100,
    is_published BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Student Submissions
CREATE TABLE submissions (
    id SERIAL PRIMARY KEY,
    assignment_id INTEGER NOT NULL REFERENCES assignments(id) ON DELETE CASCADE,
    student_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    submission_text TEXT,
    file_url VARCHAR(500),
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status assignment_status DEFAULT 'SUBMITTED',
    score INTEGER,
    feedback TEXT,
    graded_at TIMESTAMP,
    graded_by INTEGER REFERENCES users(id),
    UNIQUE(assignment_id, student_id)
);

-- Quiz Attempts
CREATE TABLE quiz_attempts (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    quiz_id INTEGER NOT NULL REFERENCES quizzes(id) ON DELETE CASCADE,
    score INTEGER,
    total_marks INTEGER,
    percentage DECIMAL(5, 2),
    passed BOOLEAN,
    attempted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    time_spent_minutes INTEGER
);

-- Student Responses to Quiz Questions
CREATE TABLE student_responses (
    id SERIAL PRIMARY KEY,
    attempt_id INTEGER NOT NULL REFERENCES quiz_attempts(id) ON DELETE CASCADE,
    question_id INTEGER NOT NULL REFERENCES quiz_questions(id) ON DELETE CASCADE,
    selected_answer_id INTEGER REFERENCES quiz_answers(id),
    is_correct BOOLEAN,
    time_spent_seconds INTEGER,
    UNIQUE(attempt_id, question_id)
);

-- Certificates
CREATE TABLE certificates (
    id SERIAL PRIMARY KEY,
    student_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    course_id INTEGER NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
    certificate_url VARCHAR(500),
    issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(student_id, course_id)
);

-- Notifications
CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    message TEXT,
    notification_type VARCHAR(50),
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP
);

-- Create Indexes for performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_courses_instructor ON courses(instructor_id);
CREATE INDEX idx_courses_level ON courses(level);
CREATE INDEX idx_courses_category ON courses(category);
CREATE INDEX idx_enrollments_student ON enrollments(student_id);
CREATE INDEX idx_enrollments_course ON enrollments(course_id);
CREATE INDEX idx_enrollments_status ON enrollments(status);
CREATE INDEX idx_student_progress_enrollment ON student_progress(enrollment_id);
CREATE INDEX idx_student_progress_lesson ON student_progress(lesson_id);
CREATE INDEX idx_quiz_attempts_student ON quiz_attempts(student_id);
CREATE INDEX idx_quiz_attempts_quiz ON quiz_attempts(quiz_id);
CREATE INDEX idx_submissions_student ON submissions(student_id);
CREATE INDEX idx_submissions_assignment ON submissions(assignment_id);
CREATE INDEX idx_submissions_status ON submissions(status);
CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_read ON notifications(is_read);

