import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrdersComponent } from './orders/orders.component';
import { OrderItemComponent } from './order-item/order-item.component';
import { OrderDetailsComponent } from './order-details/order-details.component';
import { WatchListComponent } from './watch-list/watch-list.component';
import { HttpClientModule } from '@angular/common/http';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatButtonModule} from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { WatchDetailComponent } from './watch-detail/watch-detail.component';
import { MatIconModule } from '@angular/material/icon';
import { NavbarComponent } from './navbar/navbar.component';
import {MatBadgeModule} from '@angular/material/badge';




@NgModule({
  declarations: [
    OrdersComponent,
    OrderItemComponent,
    OrderDetailsComponent,
    WatchDetailComponent,
    WatchListComponent,
    NavbarComponent
  ],
  imports: [
    CommonModule,

    FormsModule,
    RouterModule,
    CommonModule,
    HttpClientModule,
    MatGridListModule,
    MatBadgeModule,
    MatButtonModule,
    FormsModule,
    CommonModule,
    MatIconModule
  ],
  exports:[
    OrdersComponent,
    OrderItemComponent,
    WatchListComponent,
    WatchDetailComponent,
    NavbarComponent
  ],
})

export class SharedModule { }
