import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderSummaryDto } from 'src/app/interfaces/order-summary-dto';
import { OrderService } from '../services/order.service';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css'],
})
export class OrdersComponent implements OnInit {
  private totalItems: number;
  private itemsPerPage: number;
  private orders: OrderSummaryDto[];


  isCustomerLoggedIn: boolean;
  isAdminLoggedIn: boolean;
  perPage: string;
  itemsPerPageOptions: number[];
  totalPages: number;
  pagedOrders: OrderSummaryDto[];
  pages: number[];
  currentPage: number;
  errorMsg: string;
  isLoading:boolean=true;
  constructor(
    private orderService: OrderService,
    private router: Router,
    private authService: AuthService
  ) {
    this.orders = [];
    this.isCustomerLoggedIn = false;
    this.isAdminLoggedIn = false;
    this.pagedOrders = [];
    this.pages = [];
    this.errorMsg = '';
    this.itemsPerPage = 2;
    this.perPage = '2';
    this.currentPage = 1;
    this.totalPages = 0;
    this.totalItems = 0;
    this.itemsPerPageOptions = [2, 4, 6, 8, 10, 20];
  }
  ngOnInit(): void {
    this.setLoggedIn();
    this.orderService.fetchAll().subscribe({
      next: (response) => {
        this.orders = response;
        this.filterDataIfAdminLoggedIn();
        this.totalItems = this.orders.length;
        this.calculateTotalPages();
        this.calculatePages();
        this.displayPagedOrders();
        this.isLoading=false;
      },
      error: (err) => {
        this.isLoading=false;
        if (err.status === 403) {
          this.errorMsg =
            'Session timeout please login again Redirecting to login please wait';
          setTimeout(() => {
            this.authService.logout();
            this.router.navigateByUrl('/login');
          }, 3000);
        } else if (err.status === 404) {
        } else {
          this.errorMsg = 'Something went wrong';
        }
      },
    });
  }

  changeItemsPerPage(): void {
    this.itemsPerPage = Number(this.perPage);
    this.calculateTotalPages();
    this.calculatePages();
    if (this.currentPage > this.pages.length) {
      this.currentPage = 1;
    }
    this.displayPagedOrders();
  }
  private calculatePages(): void {
    this.pages = [];
    for (let i = 1; i <= this.totalPages; i++) this.pages.push(i);
  }

  goToPage(page: number): void {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.displayPagedOrders();
    }
  }
  previousPage(): void {
    this.goToPage(this.currentPage - 1);
  }
  nextPage() {
    this.goToPage(this.currentPage + 1);
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

  private calculateTotalPages(): void {
    this.totalPages = Math.ceil(this.totalItems / this.itemsPerPage);
  }

  private filterDataIfAdminLoggedIn(): void {
    if (this.isAdminLoggedIn) {
      this.orders = this.orders.filter(
        (order) => order.orderStatus !== 'DELIVERED'
      );
    }
  }

  private displayPagedOrders(): void {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.pagedOrders = this.orders.slice(startIndex, endIndex);
  }

}
