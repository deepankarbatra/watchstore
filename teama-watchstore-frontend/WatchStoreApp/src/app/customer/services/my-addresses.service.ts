import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AddressDto } from 'src/app/interfaces/address';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';
@Injectable({
  providedIn: 'root'
})
export class MyAddressesService {
  url: string;
  URL: string;

  constructor(
    private http: HttpClient,
    private auth: AuthService
  ) {
    const baseUrl = 'http://localhost:8083';
    const userId = auth.getDecodedUser().email;
    const queryParams = { userId: userId };
    this.url = `${baseUrl}/addresses?userId=${queryParams.userId}`;
    this.URL = `${baseUrl}/addresses`
  }

  saveAddress(addressDto: AddressDto): Observable<string> {
    return this.http.post(this.URL, addressDto, {
      responseType: 'text'
    })
  }

  getAddresses(): Observable<AddressDto[]> {
    return this.http.get<AddressDto[]>(this.url);
  }

  updateAddress(addressId: any, addressDto: AddressDto): Observable<string> {
    const editUrl = `${this.URL}/${addressId}`;
    return this.http.put(editUrl, addressDto, {
      responseType: 'text'
    })
  }

  deleteAddress(addressId: any) {
    const deleteUrl = `${this.URL}/${addressId}`;
    return this.http.delete(deleteUrl, {
      responseType: 'text'
    })
  }
}
