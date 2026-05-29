import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private authSubject = new BehaviorSubject<any>(null);
  public auth$ = this.authSubject.asObservable();

  constructor(private http: HttpClient) {
    this.loadStoredAuth();
  }

  private loadStoredAuth() {
    const stored = localStorage.getItem('auth_token');
    if (stored) {
      this.authSubject.next(JSON.parse(stored));
    }
  }

  register(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data).pipe(
      tap((response: any) => {
        if (response.accessToken) {
          localStorage.setItem('auth_token', JSON.stringify(response));
          localStorage.setItem('access_token', response.accessToken);
          this.authSubject.next(response);
        }
      })
    );
  }

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { email, password }).pipe(
      tap((response: any) => {
        if (response.accessToken) {
          localStorage.setItem('auth_token', JSON.stringify(response));
          localStorage.setItem('access_token', response.accessToken);
          this.authSubject.next(response);
        }
      })
    );
  }

  logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('access_token');
    this.authSubject.next(null);
  }

  getAccessToken(): string | null {
    return localStorage.getItem('access_token');
  }

  getCurrentUser(): any {
    return this.authSubject.value;
  }

  isAuthenticated(): boolean {
    return !!this.getAccessToken();
  }
}

