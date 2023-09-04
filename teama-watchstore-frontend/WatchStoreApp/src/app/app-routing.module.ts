import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './core/login/login.component';
import { ProfileComponent } from './core/profile/profile.component';
import { RegisterComponent } from './core/register/register.component';
import { WatchDetailComponent } from './shared/watch-detail/watch-detail.component';
import { WatchListComponent } from './shared/watch-list/watch-list.component';

import { InventoryStatusComponent } from './admin/inventory-status/inventory-status.component';
import { AddressComponent } from './customer/address/address.component';
import { CartComponent } from './customer/cart/cart.component';
import { AddWatchComponent } from './admin/add-watch/add-watch.component';
import { ViewWatchComponent } from './admin/view-watch/view-watch.component';
import { MyAddressesComponent } from './customer/my-addresses/my-addresses.component';
import { AddAddressComponent } from './customer/add-address/add-address.component';
import { authGuard } from './guard/auth.guard';
import { OrdersComponent } from './shared/orders/orders.component';
import { OrderDetailsComponent } from './shared/order-details/order-details.component';

const routes: Routes = [
  {
    path: 'orders',
    component: OrdersComponent,
    canActivate: [authGuard],
  },
  {
    path: 'orders/:id',
    component: OrderDetailsComponent,
    canActivate: [authGuard],
  },

  { path: 'register', component: RegisterComponent },
  {
    path: 'product-list',
    component: WatchListComponent,
  },
  { path: 'cart', component: CartComponent },
  {
    path: 'address',
    component: AddressComponent,
    canActivate: [authGuard],
    data: { role: 'CUSTOMER' },
  },
  { path: 'login', component: LoginComponent },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [authGuard],
    data: { role: 'CUSTOMER' },
  },
  { path: 'logout', component: LoginComponent },
  { path: 'product-detail/:modelNumber', component: WatchDetailComponent },
  {
    path: 'inventory-status',
    component: InventoryStatusComponent,
    canActivate: [authGuard],
    data: { role: 'ADMIN' },
  },
  {
    path: 'addresses',
    component: MyAddressesComponent,
    canActivate: [authGuard],
    data: { role: 'CUSTOMER' },
  },
  // { path: 'add-watch', component: AddWatchComponent },
  // { path: 'view-watch', component: ViewWatchComponent },
  {
    path: '**',
    component: WatchListComponent,
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
