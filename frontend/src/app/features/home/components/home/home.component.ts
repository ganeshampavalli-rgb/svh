import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'svh-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="home-container">
      <nav class="navbar">
        <div class="nav-content">
          <h1>Sri Venkateswara Home Tutions</h1>
          <div class="nav-links">
            <a routerLink="/courses">Courses</a>
            <a routerLink="/auth/login" class="btn-login">Login</a>
            <a routerLink="/auth/register" class="btn-register">Register</a>
          </div>
        </div>
      </nav>

      <section class="hero">
        <div class="hero-content">
          <h2>Welcome to Sri Venkateswara Home Tutions</h2>
          <p>Learn from Nursery to B.Tech with expert instructors</p>
          <p class="tagline">Specialized in Java Full Stack, AWS, and Angular</p>
          <a routerLink="/courses" class="btn-explore">Explore Courses</a>
        </div>
      </section>

      <section class="features">
        <h2>Our Courses</h2>
        <div class="courses-grid">
          <div class="course-card">
            <h3>School Courses</h3>
            <p>Nursery to 10th Standard comprehensive curriculum</p>
          </div>
          <div class="course-card">
            <h3>Junior College</h3>
            <p>Prepare for JNTU and competitive exams</p>
          </div>
          <div class="course-card">
            <h3>Professional Training</h3>
            <p>Java Full Stack, AWS, Angular masterclasses</p>
          </div>
        </div>
      </section>

      <section class="features">
        <h2>Why Choose Us?</h2>
        <div class="features-grid">
          <div class="feature">
            <h3>Expert Instructors</h3>
            <p>Learn from experienced professionals</p>
          </div>
          <div class="feature">
            <h3>Flexible Learning</h3>
            <p>Study at your own pace, anytime, anywhere</p>
          </div>
          <div class="feature">
            <h3>Affordable Pricing</h3>
            <p>Quality education at reasonable prices</p>
          </div>
          <div class="feature">
            <h3>Certifications</h3>
            <p>Earn recognized certificates on completion</p>
          </div>
        </div>
      </section>

      <footer class="footer">
        <p>&copy; 2026 Sri Venkateswara Home Tutions. All rights reserved.</p>
      </footer>
    </div>
  `,
  styleUrls: ['./home.component.scss']
})
export class HomeComponent { }

