import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Plante } from '../model/plante.model';
import { Comment } from '../model/comment.model';

@Injectable({
  providedIn: 'root'
})
export class PlantService {
  private apiUrl = 'http://localhost:8080/api/plantes'; 

  constructor(private http: HttpClient) {}

  // Method to retrieve all plants
  getAllPlants(): Observable<Plante[]> {
    return this.http.get<Plante[]>(`${this.apiUrl}/afficher`);
  }

  // Method to search plants by name
  searchPlantByName(nom: string): Observable<Plante[]> { 
    return this.http.get<Plante[]>(`${this.apiUrl}/rechercher?nom=${nom}`);
  }

  // Method to search plants by properties
  searchPlantByPropriete(proprietes: string): Observable<Plante[]> {
    return this.http.get<Plante[]>(`${this.apiUrl}/rechercherproprietes?proprietes=${proprietes}`);
  }

  // Method to search plants by usage
  searchPlantByUtilisation(utilisation: string): Observable<Plante[]> {
    return this.http.get<Plante[]>(`${this.apiUrl}/rechercherutilisation?utilisation=${utilisation}`);
  }

  // Method to recommend plants based on properties and precautions
  getRecommendedPlants(proprietes: string, precaution: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/recommender?proprietes=${proprietes}&precaution=${precaution}`);
  }

  // Create Authorization headers
  private createAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('authToken'); // Retrieve token from localStorage
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  // Method to send message to a plant (affecterMessage)
  affecterMessage(id: number, messageData: { message: string }): Observable<Plante> {
    const userData = localStorage.getItem('userData'); // Retrieve user data from localStorage
    const currentUser = userData ? JSON.parse(userData) : null;  // Parse user data if it exists
  
    if (!currentUser) {
      console.error('No user data found in localStorage.');
      return throwError(() => new Error('User not logged in'));
    }
  
    // Attach the current user data to the message
    const updatedMessageData = {
      ...messageData,  // Spread existing message data
      user: currentUser // Attach user to the message data
    };
  
    const headers = this.createAuthHeaders();  // Get headers with token
    
    return this.http.put<Plante>(`${this.apiUrl}/affecterMessage/${id}`, updatedMessageData, { headers });
  }

  //new 

  addComment(planteId: number, content: string): Observable<any> {
    const headers = this.createAuthHeaders();  // Inclure le jeton JWT pour l'authentification
    const body = { content };  // Le contenu du commentaire envoyé comme objet JSON
    return this.http.post<any>(`${this.apiUrl}/plantes/${planteId}/add-comment`, body, { headers });
  }
  
/*
  getCommentsForPlante(planteId: number): Observable<any[]> {
    const headers = this.createAuthHeaders(); // Inclure le jeton JWT si nécessaire
    return this.http.get<any[]>(`${this.apiUrl}/plantes/${planteId}/comments`, { headers });
  }

  */
 /*
  getCommentsForPlante(planteId: number): Observable<Comment[]> {
    const headers = this.createAuthHeaders(); // Inclure le jeton JWT si nécessaire
    return this.http.get<Comment[]>(`${this.apiUrl}/plantes/${planteId}/comments`, { headers });
  }
  */

  getCommentsForPlante(planteId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/plantes/${planteId}/comments`);
  }



}
