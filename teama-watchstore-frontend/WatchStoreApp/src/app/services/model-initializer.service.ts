import { Injectable } from '@angular/core';
import { OrderDto } from '../interfaces/order-dto';
import { OrderSummaryDto} from '../interfaces/order-summary-dto';
import watchDto from '../interfaces/watchDto';
import { UserDto } from '../interfaces/user-dto';
import { AddressDto } from '../interfaces/address';

@Injectable({
  providedIn: 'root'
})
export class ModelInitializerService {

  constructor() { }

  getOrderSummaryDto():OrderSummaryDto
  {
    return {
      id:'',
      amount:0,
      orderStatus:'',
      quantity:0,
      user:this.getUserDto(),
      watch:this.getWatchDto(),
    }
  }
  gerOrderDto():OrderDto{
    return {
    id:'',
    amount:0,
    orderStatus:'',
    quantity:0,
    timestamp:new Date(),
    statusTimestamp:new Date(),
    user:this.getUserDto(),
    watch:this.getWatchDto(),
    deliveryAddress:this.getAddressDto()
    }
  }
  getAddressDto(): AddressDto {
    return {
    addressId:0,
    city: '',
    streetName: '',
    country: '',
    state: '',
    pincode: 0,
    landmark: '',
    phoneNumber: ''
    }
  }
  getWatchDto(): watchDto {
    return {
    modelNumber:'',
    watchName: '',
    watchBrand: '',
    watchType: '',
    stockQuantity:0,
    price: 0,
    availableStatus:false,
    imagePathList: []  
    }
  }
  getUserDto(): UserDto 
  {
    return {
      emailId: '',
      name: '',
      phoneNumber:  ''
    }
  }
}
