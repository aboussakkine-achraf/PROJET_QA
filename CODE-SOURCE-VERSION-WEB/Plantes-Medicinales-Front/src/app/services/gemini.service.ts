import { Injectable } from '@angular/core';
import axios from 'axios';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GeminiService {
  private apiUrl = 'https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent'; // Updated URL

  constructor() {}

  async getPlantRecommendations(prompt: string): Promise<any> {
    try {
      const response = await axios.post(
        `${this.apiUrl}?key=${environment.geminiApiKey}`, // Adding API key to the URL
        { 
          contents: [{ parts: [{ text: prompt }] }] // Body format as per your original request
        },
        {
          headers: {
            'Content-Type': 'application/json',
          }
        }
      );
      return response.data; // Return the API response
    } catch (error) {
      console.error('Error during Gemini AI request:', error);
      throw error;
    }
  }
}
