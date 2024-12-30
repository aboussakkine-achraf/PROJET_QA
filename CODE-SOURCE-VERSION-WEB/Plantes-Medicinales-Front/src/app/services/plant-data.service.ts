import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'  // Ensures the service is available app-wide
})
export class PlantDataService {
  private plants: any[] = [];
  private plantsToAvoid: any[] = [];

  setPlants(plants: any[], plantsToAvoid: any[]): void {
    this.plants = plants;
    this.plantsToAvoid = plantsToAvoid;
  }

  getPlants(): any[] {
    return this.plants;
  }

  getPlantsToAvoid(): any[] {
    return this.plantsToAvoid;
  }
}
