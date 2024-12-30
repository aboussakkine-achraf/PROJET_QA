import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Plante } from '../model/plante.model';
import { PlantService } from '../services/plant.service'; // Assurez-vous d'importer votre service
import { Comment } from '../model/comment.model';

@Component({
  selector: 'app-dialog-content-plant-details',
  templateUrl: './dialog-content-plant-details.component.html',
  styleUrls: ['./dialog-content-plant-details.component.css']
})
export class DialogContentPlantDetailsComponent implements OnInit {
  comments: Comment[] = []; // Déclarer une propriété pour les commentaires
  message: string = ''; // Message que l'utilisateur peut envoyer

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: Plante,
    private plantService: PlantService // Injecter le service PlantService
  ) {}

  ngOnInit(): void {
    // Appeler la méthode pour récupérer les commentaires de la plante dès que le composant est initialisé
    this.getComments();
  }

  // Méthode pour récupérer les commentaires
  getComments(): void {
    this.plantService.getCommentsForPlante(this.data.id).subscribe({
      next: (comments: Comment[]) => {
        this.comments = comments; // Stocker les commentaires dans la propriété 'comments'
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des commentaires:', err);
      }
    });
  }

  // Méthode pour ajouter un commentaire
  sendMessage(): void {
    if (this.message.trim()) { // Vérifier que le message n'est pas vide
      this.plantService.addComment(this.data.id, this.message).subscribe({
        next: (response) => {
          // Une fois le commentaire ajouté avec succès, récupérer les commentaires actualisés
          this.getComments();
          this.message = ''; // Réinitialiser le champ du message
          console.log('Message envoyé:', response);
        },
        error: (err) => {
          console.error('Erreur lors de l\'ajout du commentaire:', err);
        }
      });
    } else {
      console.log('Le message est vide');
    }
  }
}
