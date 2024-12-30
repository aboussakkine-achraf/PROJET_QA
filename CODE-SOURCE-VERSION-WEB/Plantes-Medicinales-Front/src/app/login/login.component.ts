import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credentials = { email: '', password: '' }; // Define a credentials object
  errorMessage: string = '';
  loginSuccess: boolean = false ;

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        const token = response.token; 
        this.authService.storeToken(token); 
        this.loginSuccess = true ;
        alert('Login successful!');
        this.router.navigate(['/home']); 
      },
      error: (error) => {
        console.error('Login failed', error);
        this.errorMessage = 'Invalid email or password';
        this.loginSuccess = false ;
      }
    });
  }
}
