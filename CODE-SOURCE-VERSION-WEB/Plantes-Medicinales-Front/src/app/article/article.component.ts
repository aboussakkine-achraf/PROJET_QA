import { Component, OnInit } from '@angular/core'; 
import { Router } from '@angular/router'; // Import the Router service
import { MatDialog } from '@angular/material/dialog';
import { ArticleService } from '../services/article.service'; 
import { Article } from '../model/article.model'; 
import { DialogContentArticleDetailsComponent } from '../dialog-content-article-details/dialog-content-article-details.component';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css'],
})
export class ArticleComponent implements OnInit {
  articles: Article[] = [];
  isLoading: boolean = true;
  isSidenavOpened: boolean = false;

  constructor(
    private router: Router, // Inject the Router service
    private dialog: MatDialog,
    private articleService: ArticleService
  ) {}

  ngOnInit(): void {
    this.loadArticles();
  }

  loadArticles(): void {
    this.isLoading = true; // Start loading
    this.articleService.getAllArticles().subscribe(
      (data: Article[]) => {
        this.articles = data;
        this.isLoading = false; // Loading complete
        console.log('Articles loaded:', this.articles);
      },
      (error) => {
        this.isLoading = false; // Stop loading on error
        console.error('Erreur lors de la récupération des articles :', error);
      }
    );
  }

  openDialog(article: Article): void {
    const dialogRef = this.dialog.open(DialogContentArticleDetailsComponent, {
      data: article
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  consulterArticle(article: Article): void {
    alert(`Consulting article: ${article.title} by ${article.userFullName}`);
  }

  // Navigation methods for your redirections
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

  toggleSidenav(): void {
    this.isSidenavOpened = !this.isSidenavOpened;
  }
}
