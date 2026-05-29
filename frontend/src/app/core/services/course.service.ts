import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  private apiUrl = 'http://localhost:8080/api/courses';

  constructor(private http: HttpClient) { }

  getPublishedCourses(page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get(`${this.apiUrl}/published`, { params });
  }

  getCourseById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  getCoursesByCategory(category: string, page: number = 0): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', '10');
    return this.http.get(`${this.apiUrl}/category/${category}`, { params });
  }

  getCoursesByLevel(level: string, page: number = 0): Observable<any> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', '10');
    return this.http.get(`${this.apiUrl}/level/${level}`, { params });
  }

  getInstructorCourses(instructorId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/instructor/${instructorId}`);
  }

  createCourse(courseData: any): Observable<any> {
    return this.http.post(this.apiUrl, courseData);
  }

  updateCourse(id: number, courseData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, courseData);
  }

  deleteCourse(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}

