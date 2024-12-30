import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PlantService } from '../services/plant.service';  // Ensure this import is correct
import { Router } from '@angular/router';  // Import Router to navigate to the next page
import { PlantDataService } from '../services/plant-data.service';

@Component({
  selector: 'app-recommendation-form',
  templateUrl: './recommendation-form.component.html',
  styleUrls: ['./recommendation-form.component.css']
})
export class RecommendationFormComponent implements OnInit {
  recommendationForm!: FormGroup;
  plants: any[] = [];
  plantsToAvoid: any[] = [];
  isSidenavOpened:boolean = false;

  constructor(
    private fb: FormBuilder, 
    private plantService: PlantService,  // Inject PlantService
    private plantDataService: PlantDataService,  // Inject PlantDataService
    private router: Router  // Inject Router to redirect to plant list page
  ) {}

  ngOnInit(): void {
    this.recommendationForm = this.fb.group({
      properties: ['', Validators.required],
      medicalHistory: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.recommendationForm.valid) {
      const properties = this.recommendationForm.value.properties;
      const medicalHistory = this.recommendationForm.value.medicalHistory;

      console.log('Sending request to API with:', { properties, medicalHistory });

      // Make the API call
      this.plantService.getRecommendedPlants(properties, medicalHistory).subscribe(
        (response) => {
          console.log('API response:', response);
          this.plants = response.PlantesSansPrecaution;  // Plants to recommend
          this.plantsToAvoid = response.PlantesAvecPrecaution;  // Plants to avoid

          // Store the fetched data in the shared service
          this.plantDataService.setPlants(this.plants, this.plantsToAvoid);

          // Navigate to the plant list page after the data is fetched
          this.router.navigate(['/plant-list']);
        },
        (error) => {
          console.error('Error fetching recommended plants:', error);
        }
      );
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
