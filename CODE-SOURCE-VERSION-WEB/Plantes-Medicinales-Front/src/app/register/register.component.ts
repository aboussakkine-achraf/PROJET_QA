import { Component } from '@angular/core';
import { user } from '../model/user.model';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-component',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  user: user = {
    fullName: '',
    email: '',
    password: '',
  };

  registrationSuccess: boolean = false; // To control the success message

  constructor(private authService: AuthService , private router: Router) {}

  onSubmit(): void {
    this.authService.register(this.user).subscribe(
      (response) => {
        console.log('User registered successfully', response);
        this.registrationSuccess = true; // Show success message
        this.router.navigate(['/login']); 
      },
      (error) => {
        console.error('Error during registration', error);
        this.registrationSuccess = false; // Optionally, handle error feedback
      }
    );
  }
}
