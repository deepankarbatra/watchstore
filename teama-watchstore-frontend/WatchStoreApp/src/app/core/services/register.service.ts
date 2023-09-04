import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegisterDto } from 'src/app/interfaces/register-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(
    private http: HttpClient
  ) { }

  registerUser(registerDto: RegisterDto): Observable<string> {
    return this.http.post('http://localhost:8083/users', registerDto, {
      responseType: 'text'
    })
  }
}
