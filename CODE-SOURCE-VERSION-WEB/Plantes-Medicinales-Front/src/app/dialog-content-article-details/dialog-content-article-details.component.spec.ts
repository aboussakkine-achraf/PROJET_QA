import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogContentArticleDetailsComponent } from './dialog-content-article-details.component';

describe('DialogContentArticleDetailsComponent', () => {
  let component: DialogContentArticleDetailsComponent;
  let fixture: ComponentFixture<DialogContentArticleDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogContentArticleDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogContentArticleDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
