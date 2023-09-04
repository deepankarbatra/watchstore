import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {

  private apiUrl = 'http://localhost:8083';

  constructor(private http: HttpClient) {}

  getNotifications(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl+'/notifications');
  }

  getUserNotifications(email:string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/notifications/${email}`);
  }
}
