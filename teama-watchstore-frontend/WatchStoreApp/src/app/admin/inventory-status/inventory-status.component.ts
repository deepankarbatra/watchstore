import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef, AfterViewInit} from '@angular/core';
import { Chart } from 'chart.js';

import { StockStatus } from 'src/app/interfaces/stock-status';
import Watch from 'src/app/interfaces/watch-DTO';
import { WatchService } from 'src/app/shared/services/watch.service';

@Component({
  selector: 'app-inventory-status',
  templateUrl: './inventory-status.component.html',
  styleUrls: ['./inventory-status.component.css']
})
export class InventoryStatusComponent implements OnInit {
  public chart: any;
  public watches: Watch[]=[];
  public stockStatus: StockStatus={
    inStock:0,
    outOfStock:0
  };


  constructor(private watchService :WatchService){

  }
  ngOnInit(): void {
    this.watchService.getAllWatches().subscribe({
      next: (response: Watch[]) => {
        this.watches = response;
        this.updateStockStatus();
        this.createChart();
      },
      error: (err: HttpErrorResponse) => {
      }
    });
  }
  
  updateStockStatus(): void {
    this.stockStatus.inStock = 0;
    this.stockStatus.outOfStock = 0;
  
    this.watches.forEach(watch => {
      if (watch.availableStatus) {
        this.stockStatus.inStock = this.stockStatus.inStock + 1;
      } else {
        this.stockStatus.outOfStock = this.stockStatus.outOfStock + 1;
      }
    });
    const total=this.stockStatus.inStock+this.stockStatus.outOfStock;
    this.stockStatus.inStock=(this.stockStatus.inStock/total)*100;
    this.stockStatus.outOfStock=(this.stockStatus.outOfStock/total)*100;
  }
  
  createChart(): void {
    
    this.chart = new Chart("MyChart", {
      type: 'pie',
      data: {
        labels: ['In Stock %', 'Out of stock %'],
        datasets: [{
          label: 'My First Dataset',
          data: [this.stockStatus.inStock, this.stockStatus.outOfStock],
          backgroundColor: ['#36A2EB', '#FF6384 '],
          hoverOffset: 4
        }],
      },
      options: {
        aspectRatio: 2.5
      }
    });
  }
  
}


