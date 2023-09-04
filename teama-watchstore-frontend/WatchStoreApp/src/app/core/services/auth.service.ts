import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { LoggedUser } from 'src/app/interfaces/LoggedUser';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LoginUser } from 'src/app/interfaces/LoginUser';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  decodedUser: LoggedUser;
  jwtHelper = new JwtHelperService();

  constructor(private http: HttpClient,private router: Router) {
    this.decodedUser = {
      email: '',
      role: '',
    };
  }

  setToken(token: string): void {
    sessionStorage.setItem('token', token);
  }

  setRole()
  {
   sessionStorage.setItem('role', this.getDecodedUser().role);
  }

  getRole(): string|null {

    return sessionStorage.getItem('role');

  }

  removeToken(): void {
    sessionStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return token ? true : false;
  }

  getToken(): string {
    return sessionStorage.getItem('token') || '';
  }

  getDecodedUser(): LoggedUser {
    const token = this.getToken();
  
    if (!token) {
      this.decodedUser.email = '';
      this.decodedUser.role = '';
      return this.decodedUser;
    }
  
    const decodedToken = this.jwtHelper.decodeToken(token);
  
    this.decodedUser.email = decodedToken.sub || '';
    this.decodedUser.role = decodedToken.role || '';
  
    return this.decodedUser;
  }
  logout()
  {
    this.removeToken();
    this.router.navigate(['/product-list']);
  }


}
