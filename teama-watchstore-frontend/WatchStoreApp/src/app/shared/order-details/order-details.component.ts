import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderDto } from 'src/app/interfaces/order-dto';
import { OrderService } from '../services/order.service';
import { ModelInitializerService } from 'src/app/services/model-initializer.service';
import { AuthService } from 'src/app/core/services/auth.service';
@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css'],
})
export class OrderDetailsComponent implements OnInit {
  private id: string;
  isCustomerLoggedIn: boolean;
  isAdminLoggedIn: boolean;
  order: OrderDto;
  errorMsgForOrder: string;
  errorMsgForStatus: string;
  successMsg: string;
  imagePath:string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private orderService: OrderService,
    private authService: AuthService,
    modelInitializer: ModelInitializerService
  ) {
    this.id = this.route.snapshot.paramMap.get('id')!;
    this.errorMsgForOrder = '';
    this.successMsg = '';
    this.errorMsgForStatus = '';
    this.isAdminLoggedIn = false;
    this.isCustomerLoggedIn = false;
    this.imagePath='';
    this.order = modelInitializer.gerOrderDto();
  }
  ngOnInit(): void {
    this.setLoggedIn();
    this.orderService.fetch(this.id).subscribe({
      next: (response) => {
        this.order = response;
        this.imagePath=this.order.watch.imagePathList[0];
      },
      error: (err) => {
        if (err.status === 404) {
          this.errorMsgForOrder =
            err.error.message + ' Redirecting in 3 seconds';
          this.redirect();
        } else if (err.status === 403) {
          this.errorMsgForOrder =
            'Session timeout please login again redirecting in 3 seconds';
          setTimeout(() => {
            this.authService.logout();
            this.router.navigateByUrl('login')}, 3000);
        } else {
          this.errorMsgForOrder = 'Something went wrong';
        }
      },
    });
  }

  updateStatus(status: string): void {
    this.orderService.updateStatus(this.order.id, status).subscribe({
      next: (response) => {
        this.order.orderStatus = status.toUpperCase();
        this.order.statusTimestamp=new Date();
        this.successMsg = response.message;
        window.scrollTo({ top: 0, behavior: 'smooth' });
        setTimeout(() => (this.successMsg = ''), 3000);
      },
      error: (err) => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        if (err.status === 403) this.errorMsgForStatus = 'Unauthorized access';
        else if (err.status === 400)
          this.errorMsgForStatus = err.error.message + ' Bad Request';
        else this.errorMsgForStatus = 'Something went wrong';
        setTimeout(() => (this.errorMsgForStatus = ''), 3000);
      },
    });
  }

  private setLoggedIn(): void {
    const role = this.authService.getDecodedUser().role;
    if (role === 'ADMIN') {
      this.isAdminLoggedIn = true;
      this.isCustomerLoggedIn = false;
    } else if (role === 'CUSTOMER') {
      this.isAdminLoggedIn = false;
      this.isCustomerLoggedIn = true;
    }
  }
  private redirect(): void {
    setTimeout(() => {
      this.router.navigateByUrl("orders");
    }, 3000);
  }

}
