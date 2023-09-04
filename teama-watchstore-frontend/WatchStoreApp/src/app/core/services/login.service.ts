import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginUser } from 'src/app/interfaces/LoginUser';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }


  url = "http://localhost:8083"
  loginUser(user: LoginUser): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(this.url+'/login', user, {
      responseType: 'json',
    });
  }
}
