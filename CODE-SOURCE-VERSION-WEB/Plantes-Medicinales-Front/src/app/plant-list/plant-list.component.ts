import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';  // Import MatDialog
import { PlantDataService } from '../services/plant-data.service';
import { PlantDetailDialogComponent } from '../plant-detail-dialog/plant-detail-dialog.component'; // Import your dialog component

@Component({
  selector: 'app-plant-list',
  templateUrl: './plant-list.component.html',
  styleUrls: ['./plant-list.component.css']
})
export class PlantListComponent implements OnInit {
  plants: any[] = [];
  plantsToAvoid: any[] = [];

  constructor(
    private plantDataService: PlantDataService,
    private dialog: MatDialog  // Inject MatDialog
  ) {}

  ngOnInit(): void {
    // Get the plant data from the shared service
    this.plants = this.plantDataService.getPlants();
    this.plantsToAvoid = this.plantDataService.getPlantsToAvoid();
  }

  // Open dialog to show more plant details
  openDialog(plant: any): void {
    this.dialog.open(PlantDetailDialogComponent, {
      data: plant  // Pass the selected plant data to the dialog
    });
  }
}
