import { Injectable } from '@angular/core'; 
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { user } from '../model/user.model'; 

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl: string = 'http://localhost:8080/auth'; 

  constructor(private http: HttpClient) {}

  // Register a new user
  register(user: user): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, user);
  }

  // Login a user and store the JWT token
  login(credentials: { email: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials);
  }

  // Store the token in localStorage
  storeToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  // Retrieve the token from localStorage
  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  // Remove the token from localStorage (for logout)
  logout(): void {
    localStorage.removeItem('authToken');
  }
}
