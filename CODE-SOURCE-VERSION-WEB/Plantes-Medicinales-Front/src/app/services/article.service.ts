import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Article } from '../model/article.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private baseUrl = 'http://localhost:8080/api/articles'; 

  constructor(private http: HttpClient) { }

  getAllArticles(): Observable<Article[]> {
    const headers = this.createAuthHeaders();
    return this.http.get<Article[]>(`${this.baseUrl}`, { headers });
  }

  createArticle(
    title: string,
    content: string,
    image?: File
  ): Observable<any> {
    const formData = new FormData();
    formData.append('title', title);
    formData.append('content', content);
    if (image) {
      formData.append('image', image);
    }

    const headers = this.createAuthHeaders();
    return this.http.post(`${this.baseUrl}/ajouter`, formData, { headers });
  }

  private createAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken'); // Adjust based on how you store the token
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }
}
