import { Subscription, catchError, of } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { LoggedUser } from 'src/app/interfaces/LoggedUser';
import { AuthService } from 'src/app/core/services/auth.service';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LoginService } from '../services/login.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CartService } from 'src/app/customer/services/cart.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {

  jwtHelper = new JwtHelperService();
  loginUser: FormGroup = new FormGroup({});
  error: string;
  token='';

  constructor(private _authService: AuthService,private cartService:CartService,private formBuilder:FormBuilder,private _loginService: LoginService, private _router: Router, private snackBar: MatSnackBar) {
    this.error = '';
    
  }
  
  ngOnInit(): void {
    this.loginUser =this.formBuilder.group({
      email: ['', [Validators.required, Validators.pattern(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/)]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$/)]]
    });
    this.routeValidate();
  }

  onLogin() {
    this._loginService.loginUser(this.loginUser.value).subscribe(
      (response) => {
        this.token = response.message;
        this._authService.setToken(this.token);
        this.routeValidate();
        this.showMessage("You are logged in successfully!",3000);
          // this.snackBar.open('You are logged in successfully!', 'Close', { duration: 3000 });
      },
      (error) => {
        this.showMessage("Invalid ID or Password!",3000);
          // this.snackBar.open('Invalid ID or Password', 'Close', { duration: 3000 });
      }
    );
  }
  
  routeValidate() {
    const loggedUser = this._authService.getDecodedUser();
    if (loggedUser.role == 'ADMIN') {
      this._router.navigate(['']);
      return;
    }

    if (loggedUser.role == 'CUSTOMER') {
      this._router.navigate(['']);
      this.cartService.getCarts().subscribe({
        next: (response) => {
          this.cartService.setCartItemCount( response.length);
        },
        error: (err) => {},
      });
      return;
    }
  }
  get email() {
    return this.loginUser.get('email');
  }
get password() {
    return this.loginUser.get('password');
  }
  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }
}
