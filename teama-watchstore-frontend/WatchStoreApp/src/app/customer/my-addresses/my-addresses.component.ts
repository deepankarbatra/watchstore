import { Component, OnInit } from '@angular/core';
import { MyAddressesService } from '../services/my-addresses.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialog } from '@angular/material/dialog';
import { AddAddressComponent } from '../add-address/add-address.component';
import { AddressDto } from 'src/app/interfaces/address';

@Component({
  selector: 'app-my-addresses',
  templateUrl: './my-addresses.component.html',
  styleUrls: ['./my-addresses.component.css']
})
export class MyAddressesComponent implements OnInit {
  addressCount = 0;
  addresses: AddressDto[] = [];

  constructor(
    private addressService: MyAddressesService,
    private auth: AuthService,
    private snackBar: MatSnackBar,
    private dialog: MatDialog
  ) {
    this.addressCount = this.addresses.length;
  }
  ngOnInit(): void {
    this.fetchAddresses()
  }

  private fetchAddresses() {
    this.addressService.getAddresses().subscribe({
      next: (res: AddressDto[]) => {
        this.addresses = res;
      },
      error: (err) => {

      }
    })
  }

  addAddress() {
    const dialogRef = this.dialog.open(AddAddressComponent, {
      width: '45%'
    });
    dialogRef.afterClosed().subscribe((val) => {
      this.fetchAddresses();
    })

  }

  editAddress(address: any) {
    const userId = this.auth.getDecodedUser().email;
    
    this.dialog.open(AddAddressComponent, {
      width: '45%',
      data: address,
      
    }).afterClosed().subscribe(val => {
      this.fetchAddresses();
    })
  }


  deleteAddress(addressId: number) {
    this.addressService.deleteAddress(addressId).subscribe({
      next: (response) => {
        this.addresses = this.addresses.filter((address) => {
          address.addressId !== addressId;
        })
        setTimeout(() => {
          this.showMessage('Address deleted successfully', 3000);
        }, 1500);
        this.fetchAddresses();
      },
      error: (err) => {
        setTimeout(() => {
          this.showMessage('Something went wrong!', 3000);
        }, 1500);
      }
    })
  }

  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }
}
