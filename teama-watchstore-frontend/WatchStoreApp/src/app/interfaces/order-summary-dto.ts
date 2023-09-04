import { UserDto } from "./user-dto";
import watchDto from "./watchDto";

export interface OrderSummaryDto {
    id:string;
    amount:number;
    orderStatus:string;
    quantity:number;
    user:UserDto;
    watch:watchDto;
}
