import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'svh-course-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h1>Available Courses</h1>
      <p>Course listing coming soon...</p>
    </div>
  `,
  styleUrls: ['./course-list.component.scss']
})
export class CourseListComponent { }

