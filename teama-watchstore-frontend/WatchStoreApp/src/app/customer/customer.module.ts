import { ScrollingModule } from '@angular/cdk/scrolling';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { SharedModule } from '../shared/shared.module';
import { AddAddressComponent } from './add-address/add-address.component'; // or ReactiveFormsModule
import { AddressComponent } from './address/address.component';
import { CartComponent } from './cart/cart.component';
import { MyAddressesComponent } from './my-addresses/my-addresses.component';

@NgModule({
  declarations: [
    CartComponent,
    AddressComponent,
    MyAddressesComponent,
    AddAddressComponent
  ],
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatToolbarModule,
    MatIconModule,
    MatRadioModule,
    FormsModule,
    ScrollingModule,
    MatSnackBarModule,
    MatButtonModule,
    MatInputModule,
    ReactiveFormsModule,
    SharedModule
  ]
})
export class CustomerModule { }
