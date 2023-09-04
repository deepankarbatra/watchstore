import { Component, OnInit } from '@angular/core';
import { WatchService } from '../services/watch.service';
import { Router } from '@angular/router';
import Watch from 'src/app/interfaces/watch-DTO';
import { map } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { StockStatus } from 'src/app/interfaces/stock-status';
import { AddWatchComponent } from 'src/app/admin/add-watch/add-watch.component';
import { MatDialog } from '@angular/material/dialog';
import { ViewWatchComponent } from 'src/app/admin/view-watch/view-watch.component';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-watch-list',
  templateUrl: './watch-list.component.html',
  styleUrls: ['./watch-list.component.css']
})
export class WatchListComponent implements OnInit {

  allWatches: Watch[] = [];
  watches: Watch[] = [];
  brands: String[] = [];
  categories: string[] = ['ANALOGUE', 'AUTOMATIC', 'CHRONOGRAPH', 'DIGITAL', 'HYBRID', 'QUARTZ', 'SOLAR'];
  availabilities: string[] = ['Only Available'];
  sortByFilters: string[]=["Date of Arrival"];

  selectedBrands: string[] = [];
  selectedCategories: string[] = [];
  selectedAvailabilities: string[] = [];
  selectedSortBy: string[]=[];

  // Pagination properties
  currentPage: number = 1;
  itemsPerPage: number = 9;
  totalPages: number = 1;
  totalItems: number = 0;
  paginatedWatches: Watch[] = [];

  role: string = "";

  //to send data for pie-chart
  stockStatus: StockStatus = {
    inStock: 0,
    outOfStock: 0
  };

  constructor(private watchService: WatchService,
    private router: Router,
    public dialog: MatDialog,
    private _authService: AuthService
    ) {
      
  }
  ngOnInit(): void {
    this.getAllWatches();
    this.getAllBrands();
    this.role = this._authService.getDecodedUser().role;
    
  }

  public getAllBrands() {
    this.watchService.getAllWatchBrands()
      .subscribe({
        next: (response: String[]) => {
          if (response.length > 0) {
            this.brands = response;
          }
        },
        error: (err: HttpErrorResponse) => {
        }
      });
  }

  openDialog() {
    this.dialog.open(AddWatchComponent, {
    width:'35%'
    }).afterClosed().subscribe(val=>{
      this.ngOnInit();
    })
  }
  public getAllWatches() {
    this.watchService.getAllWatches()
      .subscribe({
        next: (response: Watch[]) => {
          if (response.length > 0) {
            this.allWatches = response;
            this.watches = this.allWatches;
            this.calculatePages();
          }
        },
        error: (err: HttpErrorResponse) => {
          (err.error);
        }
      });

  }

  onChangeCategory(value: any, event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    if (isChecked) {
      this.selectedCategories.push(value);
    } else {
      const index = this.selectedCategories.indexOf(value);
      if (index > -1) {
        this.selectedCategories.splice(index, 1);
      }
    }
  }

  onChangeBrands(value: any, event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    if (isChecked) {
      this.selectedBrands.push(value);
    } else {
      const index = this.selectedBrands.indexOf(value);
      if (index > -1) {
        this.selectedBrands.splice(index, 1);
      }
    }
  }

  onChangeAvailablity(value: any, event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    if (isChecked) {
      this.selectedAvailabilities.push(value);
    } else {
      const index = this.selectedAvailabilities.indexOf(value);
      if (index > -1) {
        this.selectedAvailabilities.splice(index, 1);
      }
    }
  }

  onChangeSortBy(value: any, event: Event) {
    const isChecked = (event.target as HTMLInputElement).checked;
    if (isChecked) {
      this.selectedSortBy.push(value);
    } else {
      const index = this.selectedSortBy.indexOf(value);
      if (index > -1) {
        this.selectedSortBy.splice(index, 1);
      }
    }
  }
  calculatePages() {
    this.totalItems = this.watches.length;
    this.totalPages = Math.ceil(this.totalItems / this.itemsPerPage);

    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedWatches = this.watches.slice(startIndex, endIndex);
  }


  onApplyFilter() {

    this.filterWatchesByFilters();
  }

  sortWatchesByDate(watches: Watch[]): Watch[] {
  watches.sort((b,a) => new Date(a.dateOfArrival).getTime() - new Date(b.dateOfArrival).getTime());
  return watches;
}


  filterWatchesByFilters() {
    this.watches = this.allWatches.filter((watch) => {
      const hasBrand = this.selectedBrands.length === 0 || this.selectedBrands.includes(watch.watchBrand);
      const hasWatchType = this.selectedCategories.length === 0 || this.selectedCategories.includes(watch.watchType);
      const hasAvailability = this.selectedAvailabilities.length === 0 || watch.availableStatus;

      return hasBrand && hasWatchType && hasAvailability;
    });
    
    if(this.selectedSortBy.length !== 0){
        this.watches= this.sortWatchesByDate(this.watches);
    }

    this.calculatePages();
  }

  searchWatches(event: Event) {
    const searchInput = (event.target as HTMLInputElement).value;
    const searchWords = searchInput.trim().toLowerCase().split(" ");
    if (searchWords.length === 0) {
      // Display all watches if searchInput is empty
      this.watches = this.allWatches;
    } else {
      // Filter the watches based on the search words
      this.watches = this.allWatches.filter((watch) => {
        return searchWords.some(
          (word) =>
            watch.modelNumber.toLowerCase().includes(word) ||
            watch.watchBrand.toLowerCase().includes(word) ||
            watch.watchName.toLowerCase().includes(word) ||
            watch.watchType.toLowerCase().includes(word)
        );
      });
    }

    this.calculatePages();
  }

  goToPage(pageNumber: number) {
    if (pageNumber >= 1 && pageNumber <= this.totalPages) {
      this.currentPage = pageNumber;
      this.filterWatchesByFilters();
    }
  }


  showWatchDetails(modelNumber: string) {
    this.router.navigate(['/watch-details', { modelNumber: modelNumber }]);
  }
}
