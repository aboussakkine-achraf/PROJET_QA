import { Component } from '@angular/core';
import { ArticleService } from '../services/article.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-form',
  templateUrl: './article-form.component.html',
  styleUrls: ['./article-form.component.css']
})
export class ArticleFormComponent {
  title: string = '';
  content: string = '';
  image: File | null = null; 
  isSidenavOpened: boolean = false;

  constructor(private articleService: ArticleService , private router: Router) { }

  // Handle file selection
  onFileChange(event: any): void {
    this.image = event.target.files[0];
  }

  // Submit the form to create an article
  submitArticle(): void {
    this.articleService.createArticle(this.title, this.content, this.image || undefined).subscribe({
      next: (response) => {
        console.log('Article Created Successfully', response);
        alert('Article created successfully!');
        // Optionally reset the form
        this.title = '';
        this.content = '';
        this.image = null;
      },
      error: (error) => {
        console.error('Error Creating the Article', error);
        alert('Error creating the article.');
      }
    });
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
