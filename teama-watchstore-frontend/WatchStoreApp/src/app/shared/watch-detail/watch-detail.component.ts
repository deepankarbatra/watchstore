import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { WatchCartDto } from 'src/app/interfaces/WatchCart-DTO';
import Watch from 'src/app/interfaces/watch-DTO';
import { WatchService } from '../services/watch.service';
import { AddWatchComponent } from 'src/app/admin/add-watch/add-watch.component';
import { MatDialog } from '@angular/material/dialog';
import watchUpdateDto from 'src/app/interfaces/watchUpdateDto';
import { CartService } from 'src/app/customer/services/cart.service';
import { of, switchMap } from 'rxjs';

@Component({
  selector: 'app-watch-detail',
  templateUrl: './watch-detail.component.html',
  styleUrls: ['./watch-detail.component.css'],
})
export class WatchDetailComponent implements OnInit {
  public watch: Watch = {
    modelNumber: '',
    watchName: '',
    watchBrand: '',
    watchType: '',
    stockQuantity: 0,
    price: 0,
    availableStatus: false,
    dateOfArrival: new Date(),
    images: [
      {
        imageID: 0,
        imagePath: '',
      },
    ],
  };
  public selectedImageIndex = 0;
  private modelNumber: string | null;
  private watchCartDto: WatchCartDto;
  private userId: string;
  selectedImg: string = '';

  constructor(
    private watchService: WatchService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private _authService: AuthService,
    public dialog: MatDialog,
    private cartService: CartService,
    private router: Router
  ) {
    this.modelNumber = '';
    this.watch = {
      modelNumber: '',
      watchName: '',
      watchBrand: '',
      watchType: '',
      stockQuantity: 0,
      price: 0,
      availableStatus: false,
      dateOfArrival: new Date('2000-12-12'),
      images: [],
    };

    this.watchCartDto = {
      watchModel: '',
    };

    this.userId = '';
  }
 
  /*In Angular, the snapshot approach provides a one-time snapshot of 
    the route parameter value at the moment the component is loaded. 
    If the route parameter changes after the component is already loaded, 
    the snapshot value remains unchanged.

    To detect and handle route parameter changes dynamically,
     you need to subscribe to the paramMap observable provided by the ActivatedRoute service,
      as shown in the second code snippet. By subscribing to the paramMap observable, 
      you can reactively respond to changes in the route parameter value.

    In summary, the snapshot approach provides an initial value of the route parameter,
    while subscribing to the paramMap observable allows you to detect and 
    handle changes to the route parameter dynamically.*/
  ngOnInit() {
    // this.modelNumber = this.route.snapshot.paramMap.get('modelNumber');
    // if (this.modelNumber) {
    //   this.getWatchByModel(this.modelNumber);
    // }
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) => {
        this.modelNumber = params.get('modelNumber');
        if (this.modelNumber) {
          return this.watchService.getWatchById(this.modelNumber);
        } 
        else {
          // Return an empty observable or handle the case where modelNumber is null
          return of(null); 
        }
      })
    ).subscribe({
      next: (watch: Watch | null) => {
        if(watch){
          this.watch = watch;
          this.selectedImg = this.watch.images[0].imagePath;
        }
      },
      error: (err: HttpErrorResponse) => {
        this.showMessage(err.error.message, 2000);
        this.router.navigate(['']);
      },
    });
  }
  private getWatchByModel(modelNumber: string) {
    this.watchService.getWatchById(modelNumber).subscribe({
      next: (response: Watch) => {
        this.watch = response;
        this.selectedImg = this.watch.images[0].imagePath;
      },
      error: (err: HttpErrorResponse) => {
        this.showMessage(err.error.message, 2000);
        this.router.navigate(['']);
      },
    });
  }
  public changeIndex(index: number) {
    this.selectedImageIndex = index;
    this.selectedImg = this.watch.images[index].imagePath;
  }

  private showMessage(message: string, duration: number): void {
    this.snackBar.open(message, 'Dismiss', {
      duration: duration,
    });
  }

  public addToCart() {
    this.watchCartDto = {
      watchModel: this.watch.modelNumber,
    };
    this.cartService.getCarts().subscribe({
      next: (res) => {
        const filteredCarts = res.filter((cart) => {
          return cart.watch.modelNumber == this.watch.modelNumber;
        });
        if(filteredCarts.length==0){
          this.cartService.setCartItemCount(
                this.cartService.getCartItemCount() + 1
              );
        }
      },
      error: (err) => {
        this.cartService.setCartItemCount(
          this.cartService.getCartItemCount() + 1
        );
      },
    });
    this.watchService.addToCart(this.watchCartDto).subscribe({
      next: (response) => {
        this.showMessage(response.message, 4000);
      },
      error: (err: HttpErrorResponse) => {
        this.showMessage(err.error.message, 4000);
      },
    });
  }

  public notify() {
    this.watchService
      .sendNotificationToAdmin(this.watch.modelNumber)
      .subscribe({
        next: (response) => {
          this.showMessage(response.message, 4000);
        },
        error: (err: HttpErrorResponse) => {
          this.showMessage(err.error.message, 4000);
        },
      });
  }

  isRoleUser(): boolean {
    if (this._authService.decodedUser.role === 'CUSTOMER') {
      return true;
    }
    return false;
  }

  isRoleAdmin(): boolean {
    if (this._authService.decodedUser.role === 'ADMIN') {
      return true;
    }
    return false;
  }
  onEdit(row: watchUpdateDto) {
    this.dialog
      .open(AddWatchComponent, {
        width: '35%',
        data: row,
      })
      .afterClosed()
      .subscribe((val) => {
        if (val === 'update') {
          this.getWatchByModel(row.modelNumber);
          console.log(val);
        }
      });
  }
  navigateToLogin() {
    this.router.navigate(['login']);
  }
}
