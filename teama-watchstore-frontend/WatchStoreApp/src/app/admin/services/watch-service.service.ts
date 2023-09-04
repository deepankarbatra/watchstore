import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import watchDto from 'src/app/interfaces/watchDto';
import watchUpdateDto from 'src/app/interfaces/watchUpdateDto';
@Injectable({
  providedIn: 'root'
})
export class WatchService {

  private header = {}
  private token: string = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJ0dXNoYXIwM0BuYWdhcnJvLmNvbSIsImlhdCI6MTY4NzY5NTQ1MywiZXhwIjoxNjg3ODAzNDUzfQ.3R_JwqqkFXLbTcfVHYVzLqyQCDfO1OTpi7boO-iUzYM"
  constructor(public httpClient: HttpClient, public router: Router) {
    this.header = new HttpHeaders({
      Authorization: 'Bearer ' + this.token
    })
  }

  getAllWatches() {

    return this.httpClient.get<watchDto[]>('http://localhost:8083/watches');
  }

  updateWatch(modelNumber: string, watchUpdate: watchUpdateDto) {
    return this.httpClient.put('http://localhost:8083/watches/' + modelNumber, watchUpdate, {
      headers: this.header
    })

  }

  addWatch(watchDto: watchDto) {
    return this.httpClient.post('http://localhost:8083/watches', watchDto, {
      headers: this.header,
      responseType: 'text'
    })

  }
}
