import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Cart } from 'src/app/interfaces/cart';
import { OrderSaveDto } from 'src/app/interfaces/order-save-dto';
import { AddAddressComponent } from '../add-address/add-address.component';
import { CartService } from '../services/cart.service';
import { OrderService } from '../services/order.service';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/core/services/auth.service';
import { AddressDto } from 'src/app/interfaces/address';
import { MyAddressesService } from '../services/my-addresses.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css'],
})
export class AddressComponent {
  constructor(
    private snackBar: MatSnackBar,
    private cartService: CartService,
    private addressService: MyAddressesService,
    private orderService: OrderService,
    private route: Router,
    private dialog: MatDialog,
    private authService: AuthService
  ) {}

  carts: Cart[] = [];
  totalAmount: number = 0;
  selectedAddressId: number = 0;
  userName: string = '';
  addresses: AddressDto[] = [];
  isPlaced: boolean = false;
  ngOnInit() {
    this.userName = this.authService.getDecodedUser().email;
    this.userName = this.userName.split('@').at(0)!;
    this.cartService.getProceedCarts().subscribe({
      next: (res) => {
        this.carts = res;
        this.carts.map(
          (cart) => (this.totalAmount += cart.watch.price * cart.watchQty)
        );
        this.fetchAddress();
      },
      error: (err) => {},
    });
    if (this.carts.length == 0) {
      this.route.navigate(['product-list']);
    }
  }
  placeOrder() {
    this.isPlaced = true;
    if (this.carts.length != 0) {
    }
    this.carts.forEach((cart) => {
      let orderDto: OrderSaveDto = {
        addressId: this.selectedAddressId,
        watchModelNumber: cart.watch.modelNumber,
        quantity: cart.watchQty,
      };
      this.orderService.saveOrder(orderDto).subscribe({
        next:(res)=>this.showMessage('Order placed successfully!', 3000)
      });
      this.cartService.removeCart(cart.cartId).subscribe({
        next:(res)=>this.cartService.setCartItemCount(0)
      });
    });
    this.route.navigate(['product-list']);
  }
  
  editAddress(address: AddressDto) {

    const dialogRef = this.dialog.open(AddAddressComponent, {
      width: '45%',
      data: address,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.fetchAddress();
      }
    });
  }
  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }
  private fetchAddress() {
    this.addressService.getAddresses().subscribe({
      next: (res) => {
        this.addresses = res;
        this.selectedAddressId = res[0].addressId;
      },
      error: (err) => {},
    });
  }
}
