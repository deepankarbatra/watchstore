import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';
import { Cart } from 'src/app/interfaces/cart';
import { CartSaveDto } from 'src/app/interfaces/cart-save-dto';
import { CartUpdateDto } from 'src/app/interfaces/cart-update-Dto';

@Injectable({
  providedIn: 'root',
})
export class CartService {

  URL:string="http://localhost:8083/carts";
  userId:string=""
  carts:Cart[]=[];
  cartItemCount=0;
  constructor(private http: HttpClient,private authService:AuthService) {}

  getCarts(): Observable<Cart[]> {
    this.userId=this.authService.getDecodedUser().email;
    return this.http.get<Cart[]>(`${this.URL}`);
  }

  saveCart(cartId:number,cart:CartSaveDto):Observable<string>{
    return this.http.post<string>(`${this.URL}`,cart);
  }

  modifyCart(cartId:number,cartUpdateDto:CartUpdateDto):Observable<string>{
    return this.http.put<string>(`${this.URL}/${cartId}`,cartUpdateDto);
  }

  removeCart(cartId:number):Observable<string>{
    return this.http.delete<string>(`${this.URL}/${cartId}`);
  }

  setProceedCarts(carts:Cart[]){
    this.carts=carts;
    
  }
  getProceedCarts():Observable<Cart[]>{
    return of(this.carts);
  }

  getCartItemCount():number{
    return this.cartItemCount;
  }
  setCartItemCount(cartItemCount:number){
    this.cartItemCount=cartItemCount;
  }
}
