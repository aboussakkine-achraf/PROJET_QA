import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'; 
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MedicinalPlantDetailsComponent } from './medicinal-plant-details/medicinal-plant-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DialogContentPlantDetailsComponent } from './dialog-content-plant-details/dialog-content-plant-details.component';
import { MatDialogModule } from '@angular/material/dialog';

import { PlantService } from './services/plant.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';  


import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import { MatSelectModule } from '@angular/material/select';
import { RecommendationFormComponent } from './recommendation-form/recommendation-form.component'; 
import { RouterModule } from '@angular/router';
import { PlantListComponent } from './plant-list/plant-list.component';
import { PlantDetailDialogComponent } from './plant-detail-dialog/plant-detail-dialog.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { ArticleComponent } from './article/article.component';
import { DialogContentArticleDetailsComponent } from './dialog-content-article-details/dialog-content-article-details.component';
import { ArticleFormComponent } from './article-form/article-form.component';
import { AssistantFormComponent } from './assistant-form/assistant-form.component';







@NgModule({
  declarations: [
    AppComponent,
    MedicinalPlantDetailsComponent,
    DialogContentPlantDetailsComponent,
    RecommendationFormComponent,
    PlantListComponent,
    PlantDetailDialogComponent,
    RegisterComponent,
    LoginComponent,
    ArticleComponent,
    DialogContentArticleDetailsComponent,
    ArticleFormComponent,
    AssistantFormComponent 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule ,
    MatButtonModule, 
    MatCardModule,
    MatDialogModule ,
    MatToolbarModule,
    MatInputModule,
    MatIconModule,
    FormsModule,
    HttpClientModule,
    MatSelectModule,
    ReactiveFormsModule, 
    RouterModule,
    MatFormFieldModule,
    MatSidenavModule,
    MatListModule,
   

    

    
    
  ],
  providers: [PlantService],
  bootstrap: [AppComponent]
})
export class AppModule { }
