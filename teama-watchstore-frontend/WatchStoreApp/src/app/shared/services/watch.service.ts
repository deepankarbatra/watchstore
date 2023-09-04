import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { WatchCartDto } from 'src/app/interfaces/WatchCart-DTO';
import Watch from 'src/app/interfaces/watch-DTO';

@Injectable({
  providedIn: 'root'
})
export class WatchService {
  public endpoint: string = "http://localhost:8083";
  constructor(private http: HttpClient) { }

  public getAllWatches():Observable<Watch[]>{
    return this.http.get<Watch[]>(this.endpoint+"/watches");
  }

  public getAllWatchBrands():Observable<String[]>{
    return this.http.get<String[]>(this.endpoint+"/watches/brands");
  }

  public getWatchById(modelNumber : string):Observable<Watch>{
    return this.http.get<Watch>(this.endpoint+"/watches/"+modelNumber);
  }

  public addToCart(watchCartDto:WatchCartDto): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(this.endpoint+'/carts', watchCartDto, {
      responseType: 'json',
    });
  }

  public sendNotificationToAdmin(modelNumber: string): Observable<{ message: string }>{
    return this.http.post<{ message: string }>(this.endpoint+"/notifications/"+modelNumber,{
      responseType: 'json',
    });
  }
 
}
