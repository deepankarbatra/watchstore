import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OrderSaveDto } from 'src/app/interfaces/order-save-dto';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  URL:string="http://localhost:8083/orders"
  constructor(private http:HttpClient) { }

  saveOrder(orderDto:OrderSaveDto){
return this.http.post(`${this.URL}`,orderDto);
  }
}
