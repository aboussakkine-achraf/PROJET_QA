import { user } from "./user.model";

export interface Plante {
    id: number;
    nom: string;
    image: string;
    description: string;
    proprietes: string;
    utilisation: string;
    preaucaution: string;
    message: string;
    user?: user;  
  }
  