import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MedicinalPlantDetailsComponent } from './medicinal-plant-details.component';

describe('MedicinalPlantDetailsComponent', () => {
  let component: MedicinalPlantDetailsComponent;
  let fixture: ComponentFixture<MedicinalPlantDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MedicinalPlantDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MedicinalPlantDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
