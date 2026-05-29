import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HomeComponent } from './features/home/components/home/home.component';

@Component({
  selector: 'svh-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HomeComponent],
  template: `
    <svh-home></svh-home>
  `,
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Sri Venkateswara Home Tutions';
}



