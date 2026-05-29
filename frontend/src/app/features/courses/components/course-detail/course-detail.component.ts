import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'svh-course-detail',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h1>Course Detail</h1>
      <p>Course detail content coming soon...</p>
    </div>
  `,
  styleUrls: ['./course-detail.component.scss']
})
export class CourseDetailComponent { }

