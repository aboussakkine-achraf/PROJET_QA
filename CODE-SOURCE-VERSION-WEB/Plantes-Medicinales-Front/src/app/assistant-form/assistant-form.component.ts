import { Component } from '@angular/core';
import { GeminiService } from '../services/gemini.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-assistant-form',
  templateUrl: './assistant-form.component.html',
  styleUrls: ['./assistant-form.component.css']
})
export class AssistantFormComponent {
  userQuestion: string = ''; // User input for the question
  response: string | null = null; // API response
  isSidenavOpened: boolean = false ;

  constructor(private geminiService: GeminiService , private router: Router) {}

  async onSubmit() {
    if (this.userQuestion) {
      try {
        // Call Gemini service and pass the user's question
        const result = await this.geminiService.getPlantRecommendations(this.userQuestion);
        // Set the response from the API
        this.response = result.candidates[0]?.content?.parts[0]?.text || 'No response received'; // Handle API response
      } catch (error) {
        this.response = 'An error occurred. Please try again.';
      }
    }
  }
  navigateToHome(): void {
    this.router.navigate(['/home']);
  }

  navigateToRecommendationForm(): void {
    this.router.navigate(['/recommendation-form']);
  }

  navigateToAddArticle(): void {
    this.router.navigate(['/addArticle']);
  }

  navigateToArticleList(): void {
    this.router.navigate(['/article']);
  }

  navigateToAssistantForm(): void {
    this.router.navigate(['/assistantform']);
  }

  toggleSidenav() {
    this.isSidenavOpened = !this.isSidenavOpened;
  }


}
