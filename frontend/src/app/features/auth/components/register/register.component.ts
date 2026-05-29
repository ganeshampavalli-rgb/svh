import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '@core/services/auth.service';

@Component({
  selector: 'svh-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  registerForm!: FormGroup;
  loading = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.initializeForm();
  }

  initializeForm() {
    this.registerForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      phoneNumber: [''],
      role: ['STUDENT']
    });
  }

  onSubmit() {
    if (this.registerForm.invalid) {
      this.error = 'Please fill all required fields correctly';
      return;
    }

    this.loading = true;
    this.error = '';

    this.authService.register(this.registerForm.value).subscribe({
      next: (response: any) => {
        this.loading = false;
        if (response.accessToken) {
          this.router.navigate(['/dashboard']);
        }
      },
      error: (error: any) => {
        this.loading = false;
        this.error = error.error?.message || 'Registration failed. Please try again.';
      }
    });
  }

  get firstName() {
    return this.registerForm.get('firstName');
  }

  get lastName() {
    return this.registerForm.get('lastName');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }
}

