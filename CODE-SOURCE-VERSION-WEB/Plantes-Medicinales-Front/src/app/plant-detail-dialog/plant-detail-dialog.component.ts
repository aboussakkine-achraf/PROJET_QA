import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-plant-detail-dialog',
  templateUrl: './plant-detail-dialog.component.html',
  styleUrls: ['./plant-detail-dialog.component.css']
})
export class PlantDetailDialogComponent {

  constructor(@Inject(MAT_DIALOG_DATA) public plant: any) { }

}
