import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { AuthService } from '@core/services/auth.service';

@Component({
  selector: 'svh-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="dashboard-container">
      <nav class="navbar">
        <h1>Sri Venkateswara Home Tutions</h1>
        <div class="user-info">
          <span>Welcome, {{ currentUser?.firstName }}</span>
          <button (click)="logout()" class="btn-logout">Logout</button>
        </div>
      </nav>

      <div class="container">
        <h2>Dashboard</h2>
        <p>Your enrolled courses and progress will appear here.</p>
      </div>
    </div>
  `,
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  currentUser: any;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();
    if (!this.currentUser) {
      this.router.navigate(['/auth/login']);
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}

