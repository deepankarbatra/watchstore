import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { Cart } from 'src/app/interfaces/cart';
import { CartSaveDto } from 'src/app/interfaces/cart-save-dto';
import { CartUpdateDto } from 'src/app/interfaces/cart-update-Dto';
import { AddAddressComponent } from '../add-address/add-address.component';
import { CartService } from '../services/cart.service';
import { MyAddressesService } from '../services/my-addresses.service';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent {
  cartItems: Cart[] = [];
  totalAmount: number = 0;

  constructor(
    private cartService: CartService,
    private dialog: MatDialog,
    private addressService: MyAddressesService,
    private route: Router,
    private snackBar: MatSnackBar,
    private authService: AuthService
  ) {}
  ngOnInit() {
    if (this.authService.getDecodedUser().role == 'ADMIN') {
      this.route.navigate(['']);
    }
    if (this.isUserLoggedIn()) {
      this.cartService.getCarts().subscribe({
        next: (response) => {
          this.cartItems = response;
          this.cartItems.map(
            (cart) => (this.totalAmount += cart.watch.price * cart.watchQty)
          );
        },
        error: (err) => {},
      });
    }
  }

  isUserLoggedIn(): boolean {
    return this.authService.isAuthenticated();
  }
  decrementQuantity(cartId: number, cartQty: number, watchPrice: number) {
    this.totalAmount -= watchPrice;
    this.updateCart(cartId, cartQty);
  }
  incrementQuantity(cartId: number, cartQty: number, watchPrice: number) {
    this.totalAmount += watchPrice;
    this.updateCart(cartId, cartQty);
  }
  onImage(modelNumber: string) {
    this.route.navigate([`product-detail/${modelNumber}`]);
  }

  updateCart(cartId: number, watchQty: number) {
    let cartUpdateDto: CartUpdateDto = {
      watchQty: watchQty,
    };
    this.cartService.modifyCart(cartId, cartUpdateDto).subscribe({
      next: (res) => {
        this.updateQty(cartId, watchQty);
        this.showMessage('Watch updated successfully!', 3000);
      },
      error: (err) => {
        this.navigateLogin();
      },
    });
  }
  removeCart(cartId: number) {
    this.cartItems = this.cartItems.filter((cart) => cart.cartId != cartId);
    this.totalAmount=0;
    this.cartItems.map((cart) => (this.totalAmount += cart.watch.price * cart.watchQty));
    this.cartService.setCartItemCount(this.cartService.getCartItemCount()-1);
    this.cartService.removeCart(cartId).subscribe({
      next: (res) => {
        this.showMessage('Removed successfully!', 3000);
      },
      error: (err) => {
        this.navigateLogin();
      },
    });
  }

  proceedCart() {
    this.cartService.setProceedCarts(this.cartItems);
    this.addressService.getAddresses().subscribe({
      next: (res) => {
        this.route.navigate(['address']);
      },
      error: (err) => {
        this.dialog.open(AddAddressComponent, {
          width: '45%',
        });
      },
    });
  }
  navigateHome() {
    this.route.navigate(['product-list']);
  }
  private updateQty(cartId: number, watchQty: number) {
    this.cartItems
      .filter((cart) => cart.cartId == cartId)
      .map((cart) => (cart.watchQty = watchQty));
  }
  navigateLogin() {
    this.route.navigate(['login']);
  }
  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }
}
