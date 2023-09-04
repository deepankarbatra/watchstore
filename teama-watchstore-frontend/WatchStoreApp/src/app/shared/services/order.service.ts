import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderDto } from 'src/app/interfaces/order-dto';
import { OrderSummaryDto } from 'src/app/interfaces/order-summary-dto';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private url = 'http://localhost:8083/orders';
  constructor(private httpClient: HttpClient) {}

  fetchAll(): Observable<OrderSummaryDto[]> {
    return this.httpClient.get<OrderSummaryDto[]>(this.url);
  }

  fetch(id: string): Observable<OrderDto> {
    return this.httpClient.get<OrderDto>(this.url + '/' + id);
  }

  updateStatus(id: string, status: string): Observable<{ message: string }> {
    let body = {
      status: status,
    };
    return this.httpClient.put<{ message: string }>(this.url + '/' + id, body);
  }
}
