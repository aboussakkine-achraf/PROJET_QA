import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <h1>{{ title }}</h1>
    <!-- This is where the routed components will be rendered -->
    <router-outlet></router-outlet>
  `,
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // Supprimez ou modifiez cette ligne
  title = '';  // Si vous voulez supprimer le texte
}


