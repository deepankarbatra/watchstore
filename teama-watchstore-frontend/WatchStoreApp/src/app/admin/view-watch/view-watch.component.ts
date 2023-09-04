import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import watchDto from 'src/app/interfaces/watchDto';
import { AddWatchComponent } from '../add-watch/add-watch.component';
import { WatchService } from '../services/watch-service.service';

@Component({
  selector: 'app-view-watch',
  templateUrl: './view-watch.component.html',
  styleUrls: ['./view-watch.component.css']
})
export class ViewWatchComponent {
 watch: watchDto[]=[];
constructor(
  public router: Router,
  public getallWatch: WatchService,
  public dialog: MatDialog
) {}

displayedColumns: string[] | undefined;
dataSource: any;

@ViewChild(MatPaginator) paginator: MatPaginator | undefined;

ngAfterViewInit() {}
async ngOnInit() {
  this.displayedColumns = [
    'image',
    'model',
    'name',
    'brand',
    'price',
    'action'
   
  ];
  this.getAllWatch();
}
getAllWatch()
{
this.getallWatch.getAllWatches().subscribe((result)=>
{
  this.watch=result;
  this.dataSource = new MatTableDataSource(this.watch);
  this.dataSource.paginator = this.paginator; 
});

}

applyFilter(filterValue: string) {
  this.dataSource.filter = filterValue.trim().toLocaleLowerCase();
}
editWatch(row:any){
  this.dialog.open(AddWatchComponent,{
  width:'35%',
  data:row
  }).afterClosed().subscribe(val=>{
    if(val==='update')
    {
      this.getAllWatch();
    }
  })
}

viewWatch(modelNumber:string)
{
  this.router.navigateByUrl('/product-detail/'+modelNumber);
}

openDialog() {
  this.dialog.open(AddWatchComponent, {
  width:'35%'
  }).afterClosed().subscribe(val=>{
    if(val==='save')
    {
      this.getAllWatch();
    }
  })
}
}
