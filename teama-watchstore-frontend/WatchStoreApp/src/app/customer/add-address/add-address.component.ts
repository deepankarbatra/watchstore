import { Component, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { AddressDto } from 'src/app/interfaces/address';
import { MyAddressesService } from '../services/my-addresses.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-address',
  templateUrl: './add-address.component.html',
  styleUrls: ['./add-address.component.css']
})
export class AddAddressComponent {
  addAddress: FormGroup = new FormGroup({});
  submitType: string = 'Create'
  FormType: string = 'Add Address'
  address: any;
  constructor(
    private formBuilder: FormBuilder,
    private snackBar: MatSnackBar,
    private router: Router,
    private auth: AuthService,
    private addressService: MyAddressesService,
    private dialogRef: MatDialogRef<AddAddressComponent>,
    @Inject(MAT_DIALOG_DATA) public editData: any
  ) {
  }

  addressDto: AddressDto = {
    addressId: 0,
    streetName: '',
    city: '',
    country: '',
    state: '',
    pincode: 0,
    landmark: '',
    phoneNumber: '',
  }

  ngOnInit(): void {

    this.addAddress = this.formBuilder.group({
      streetName: ['', [Validators.required]],
      city: ['', [Validators.required]],
      state: ['', [Validators.required]],
      country: ['', [Validators.required]],
      pincode: ['', [Validators.required, Validators.min(100000), Validators.max(999999), Validators.pattern('[0-9]{6}')]],
      landmark: ['', [Validators.required]],
      phoneNumber: ['', [Validators.required, Validators.min(1000000000), Validators.pattern('[0-9]{10}')]]
    });
    if (this.editData) {
      this.FormType = 'Edit Address';
      this.submitType = 'Update'
      this.addAddress.controls['streetName'].setValue(this.editData.streetName);
      this.addAddress.controls['city'].setValue(this.editData.city);
      this.addAddress.controls['country'].setValue(this.editData.country);
      this.addAddress.controls['state'].setValue(this.editData.state);
      this.addAddress.controls['pincode'].setValue(this.editData.pincode);
      this.addAddress.controls['landmark'].setValue(this.editData.landmark);
      this.addAddress.controls['phoneNumber'].setValue(this.editData.phoneNumber);


    }
  };

  AddAddress() {
    if (!this.editData) {
      if (this.addAddress.valid) {
        this.formDtoConversion();
        this.addressService.saveAddress(this.addressDto).subscribe({
          next: (response: string) => {
            setTimeout(() => {
              this.showMessage('Address added successfully', 3000);
            }, 1500);
            this.dialogRef.close('save');
          },
          error: (err) => {
            setTimeout(() => {
              this.showMessage('Something went wrong!', 3000);
            }, 1500);
          }
        })
      }
    } else {
      this.updateAddress();
    }
  }

  updateAddress() {
    if (this.addAddress.valid) {
      this.formDtoConversion();
      this.addressService.updateAddress(this.editData.addressId, this.addressDto).subscribe({
        next: (response: string) => {
          setTimeout(() => {
            this.showMessage('Address updated successfully', 3000);
          }, 1500);
          this.dialogRef.close('update');
        },
        error: (err) => {
          setTimeout(() => {
            this.showMessage('Something went wrong!', 3000);
          }, 1500);
        }
      })
    }
  }

  private formDtoConversion() {
    this.addressDto.city = this.addAddress.value.city;
    this.addressDto.country = this.addAddress.value.country;
    this.addressDto.landmark = this.addAddress.value.landmark;
    this.addressDto.phoneNumber = this.addAddress.value.phoneNumber;
    this.addressDto.pincode = Number(this.addAddress.value.pincode);
    this.addressDto.state = this.addAddress.value.state;
    this.addressDto.streetName = this.addAddress.value.streetName;
  }

  cancel() {
    this.dialogRef.close();
  }


  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }

  get streetName() {
    return this.addAddress.get('streetName');
  }

  get city() {
    return this.addAddress.get('city');
  }

  get state() {
    return this.addAddress.get('state');
  }
  get country() {
    return this.addAddress.get('country');
  }
  get pincode() {
    return this.addAddress.get('pincode');
  }
  get landmark() {
    return this.addAddress.get('landmark');
  }
  get phoneNumber() {
    return this.addAddress.get('phoneNumber');
  }

}
