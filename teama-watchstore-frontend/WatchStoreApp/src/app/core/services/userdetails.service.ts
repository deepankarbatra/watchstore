import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserdetailsService {

  constructor(private http: HttpClient) { }

  getUserDetails(email: string): Observable<any> {
    return this.http.get<any>(`http://localhost:8083/users/${email}`);
  }
}
