import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogContentPlantDetailsComponent } from '../dialog-content-plant-details/dialog-content-plant-details.component';
import { Plante } from '../model/plante.model'; 
import { PlantService } from '../services/plant.service';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-medicinal-plant-details',
  templateUrl: './medicinal-plant-details.component.html',
  styleUrls: ['./medicinal-plant-details.component.css']
})
export class MedicinalPlantDetailsComponent implements OnInit {
  plants: Plante[] = []; 
  searchTerm: string = '';
  searchType: string = 'nom';
  foundPlant?: Plante; 
  isSidenavOpened: boolean = false; 

  constructor(private dialog: MatDialog, private plantService: PlantService ,  private router: Router,) {}

  ngOnInit(): void {
    this.fetchPlants(); 
  }

  // Fetch all plants
  fetchPlants(): void {
    this.plantService.getAllPlants().subscribe(plants => {
      this.plants = plants; 
    });
  }

  
  openDialog(plant: Plante): void {
    const dialogRef = this.dialog.open(DialogContentPlantDetailsComponent, {
      data: plant 
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  
  searchPlant(): void {
    if (this.searchTerm.trim()) {
      if (this.searchType === 'nom') {
        this.plantService.searchPlantByName(this.searchTerm).subscribe(
          (plants: Plante[]) => {
            this.plants = plants; 
          },
          (error) => {
            console.error('Error fetching plants:', error);
            this.plants = []; 
          }
        );
      } else if (this.searchType === 'proprietes') {
        this.plantService.searchPlantByPropriete(this.searchTerm).subscribe(
          (plants: Plante[]) => {
            this.plants = plants; 
          },
          (error) => {
            console.error('Error fetching plants by property:', error);
            this.plants = []; 
          }
        );
      } else if (this.searchType === 'utilisation') {
        this.plantService.searchPlantByUtilisation(this.searchTerm).subscribe(
          (plants: Plante[]) => {
            this.plants = plants; 
          },
          (error) => {
            console.error('Error fetching plants by usage:', error);
            this.plants = []; 
          }
        );
      }
    } else {
      this.fetchPlants(); 
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

  toggleSidenav(): void {
    this.isSidenavOpened = !this.isSidenavOpened;
  }
 
}
