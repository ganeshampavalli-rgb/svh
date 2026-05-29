import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EnrollmentService {
  private apiUrl = 'http://localhost:8080/api/enrollments';

  constructor(private http: HttpClient) { }

  enrollCourse(enrollmentData: any): Observable<any> {
    return this.http.post(this.apiUrl, enrollmentData);
  }

  getStudentEnrollments(studentId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/student/${studentId}`);
  }

  getCourseEnrollments(courseId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/course/${courseId}`);
  }

  updateEnrollment(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, data);
  }
}

