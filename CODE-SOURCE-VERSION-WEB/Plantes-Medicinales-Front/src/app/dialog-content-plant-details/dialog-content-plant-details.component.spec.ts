import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DialogContentPlantDetailsComponent } from './dialog-content-plant-details.component';
import { MatDialogModule } from '@angular/material/dialog';

describe('DialogContentPlantDetailsComponent', () => {
  let component: DialogContentPlantDetailsComponent;
  let fixture: ComponentFixture<DialogContentPlantDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogContentPlantDetailsComponent ],
      imports: [ MatDialogModule ],  // Import MatDialogModule
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogContentPlantDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
