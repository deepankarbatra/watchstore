import {
  ApplicationRef,
  ChangeDetectorRef,
  Component,
  NgZone,
  OnInit,
  Output,
} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { NotificationDialogComponent } from 'src/app/core/notification-dialog/notification-dialog.component';
import { NotificationsService } from 'src/app/core/services/notifications.service';
import { CartService } from 'src/app/customer/services/cart.service';
import { AuthService } from '../../core/services/auth.service';
import { DialogRef } from '@angular/cdk/dialog';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  role: string = '';
  email: string = '';
  isPageRefreshed: boolean = false;
  cartItemCount = 0;

  constructor(
    private _authService: AuthService,
    private _router: Router,
    public dialog: MatDialog,
    private zone: NgZone,
    private cartService: CartService,
    private changeDetectorRef: ChangeDetectorRef,
    private appRef: ApplicationRef,
    private notificationService: NotificationsService,

  ) {
  }
  ngOnInit(): void {
    this.changeDetectorRef.markForCheck();

    this.role = this._authService.getDecodedUser().role;
    this.fetchEmail();
    this._router.events.subscribe((result: any) => {
      if (result.url) {
        this.zone.run(() => {
          this.role = this._authService.getDecodedUser().role;
          this.fetchEmail();
          this.appRef.tick();
        });
      }
    });
    if (
        this._authService.isAuthenticated() &&
        this._authService.getDecodedUser().role === 'CUSTOMER'
      ) {
        this.cartService.getCarts().subscribe({
          next: (res) => {
            this.cartItemCount = res.length;
            this.cartService.setCartItemCount(this.cartItemCount);
          },
          error: (err) => {
            this.cartItemCount=0;
          },
        });
      }
    setInterval(() => {
      this.changeDetectorRef.markForCheck();
      this.cartItemCount=this.cartService.getCartItemCount();
    }, 1000);
  }

  onLogin() {}

  fetchEmail(): void {
    this.email = this._authService.getDecodedUser().email;
  }

  logout() {
    this._authService.logout();
    this.cartService.setCartItemCount(0);
    this._router.navigate(['login']);
  }

  isNavbarCollapsed = true;
  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  public isRoleAdmin(): boolean {
    if ('ADMIN' === this._authService.getDecodedUser().role) {
      return true;
    }
    return false;
  }

  public isRoleCustomer(): boolean {
    if ('CUSTOMER' === this._authService.getDecodedUser().role) {
      return true;
    }
    return false;
  }

  public isUserLoggedIn(): boolean {
    const token = this._authService.getToken();
    if (!!token && this._authService.getDecodedUser().role != null) {
      return true;
    }

    return false;
  }

  openAdminNotifications() {
    this.notificationService.getNotifications().subscribe(
      (response: any[]) => {
        console.warn(response);
        
        const dialogRef = this.dialog.open(NotificationDialogComponent, {
          width: '500px',
          data: response,
          position: {
            top: '64px',
            right: '16px',
          },
        });
  
        dialogRef.afterClosed().subscribe((result) => {
        });
      },
      (error: any) => {
        console.error('Error fetching notifications:', error);
      }
    );
  }

  

  openUserNotifications() {
    this.email = this._authService.getDecodedUser().email;
    this.role = this._authService.getDecodedUser().role;
  
    this.notificationService.getUserNotifications(this.email).subscribe(
      (response: any[]) => {
        const dialogRef = this.dialog.open(NotificationDialogComponent, {
          width: '500px',
          data: response,
          position: {
            top: '64px',
            right: '16px',
          },
        });
  
        dialogRef.afterClosed().subscribe((result) => {
        
        });
      },
      (error: any) => {
        console.error('Error fetching notifications:', error);
      }
    );
  }
  

  addItemToCart() {
    this.cartItemCount++;
  }
  removeItemFromCart() {
    if (this.cartItemCount > 0) {
      this.cartItemCount--;
    }
  }
}
