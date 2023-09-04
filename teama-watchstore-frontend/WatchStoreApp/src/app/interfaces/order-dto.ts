import { AddressDto } from "./address";
import { UserDto } from "./user-dto";
import watchDto from "./watchDto";

export interface OrderDto {
    id:string;
    amount:number;
    orderStatus:string;
    quantity:number;
    timestamp:Date;
    statusTimestamp:Date;
    user:UserDto;
    watch:watchDto;
    deliveryAddress:AddressDto;
}
