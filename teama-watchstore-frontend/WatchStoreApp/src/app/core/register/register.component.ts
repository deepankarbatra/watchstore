import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterDto } from 'src/app/interfaces/register-dto';
import { RegisterService } from '../services/register.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../services/auth.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerUser: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private registerService: RegisterService,
    private snackBar: MatSnackBar,
    private router: Router,
    private _authService:AuthService
  ) { }
  registerDto: RegisterDto = {
    emailId: '',
    name: '',
    password: '',
    role: 'customer',
    phoneNumber: ''
  }
  ngOnInit(): void {
    if(this._authService.isAuthenticated()){
      this.router.navigate(['']);
    }else{
    this.registerUser = this.formBuilder.group({
      email: ['', [Validators.required, Validators.pattern(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/)]],
      name: ['', [Validators.required, Validators.pattern('^[A-Za-z\\s]+$')]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,24}$/)]],
      phone: ['', [Validators.required, Validators.pattern('^[0-9]{10}$')]]
    });
  }
  };

  registerCustomer() {
    if (this.registerUser.valid) {
      this.formDtoConversion();
      this.registerService.registerUser(this.registerDto).subscribe({
        next: (response: any) => {
          setTimeout(() => {
            this.showMessage('User Registered successfully!', 3000);
          }, 1500);
          this.router.navigate(['/login']);

        },
        error: err => {
          this.showMessage('Failed to register. Please try again!', 3000);
        }
      })
    }
  }

  getPasswordErrorMessage(): string {
    const screenWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    if (screenWidth <= 500) {
      return "Invalid Password Format!";
    } else {
      return "Password: 8-24 characters, 1 uppercase, 1 lowercase, 1 special character.";
    }
  }
  onCancel(){
    this.router.navigate(['/login']);
  }

  private formDtoConversion() {
    this.registerDto.emailId = this.registerUser.value.email;
    this.registerDto.name = this.registerUser.value.name;
    this.registerDto.password = this.registerUser.value.password;
    this.registerDto.phoneNumber = this.registerUser.value.phone;
    this.registerDto.role = 'customer';
  }

  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }

  get email() {
    return this.registerUser.get('email');
  }

  get name() {
    return this.registerUser.get('name');
  }

  get password() {
    return this.registerUser.get('password');
  }

  get phone() {
    return this.registerUser.get('phone');
  }
}
