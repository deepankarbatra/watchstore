import { Component,Input, OnInit } from '@angular/core';
import { OrderSummaryDto } from 'src/app/interfaces/order-summary-dto';
import { ModelInitializerService } from 'src/app/services/model-initializer.service';

@Component({
  selector: 'app-order-item',
  templateUrl: './order-item.component.html',
  styleUrls: ['./order-item.component.css']
})
export class OrderItemComponent implements OnInit {

  @Input()
  order: OrderSummaryDto;
  imagePath:string;
  constructor (modelInitializer:ModelInitializerService)
  {
    this.imagePath='';
    this.order=modelInitializer.getOrderSummaryDto();
  }

  ngOnInit(): void {
    this.imagePath=this.order.watch.imagePathList[0];
  }

}
