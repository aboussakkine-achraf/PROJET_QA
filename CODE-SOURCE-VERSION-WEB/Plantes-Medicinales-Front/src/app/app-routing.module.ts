import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecommendationFormComponent } from './recommendation-form/recommendation-form.component';
import { PlantListComponent } from './plant-list/plant-list.component';  
import { MedicinalPlantDetailsComponent } from './medicinal-plant-details/medicinal-plant-details.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { ArticleComponent } from './article/article.component';
import { ArticleFormComponent } from './article-form/article-form.component';
import { AssistantFormComponent } from './assistant-form/assistant-form.component';

const routes: Routes = [
  { path: '', redirectTo: '/register', pathMatch: 'full' },  // Default route
  { path: 'recommendation-form', component: RecommendationFormComponent },
  { path: 'plant-list', component: PlantListComponent }, 
  { path: 'home', component: MedicinalPlantDetailsComponent },
  {path: 'register' , component: RegisterComponent},
  {path: 'login' , component: LoginComponent},
  {path: 'article' , component: ArticleComponent},
  {path: 'addArticle' , component:ArticleFormComponent },
  {path: 'assistantform' , component:AssistantFormComponent},
  { path: '**', redirectTo: '/register' }
  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
