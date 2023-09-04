import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-notification-dialog',
  templateUrl: './notification-dialog.component.html',
  styleUrls: ['./notification-dialog.component.css']
})
export class NotificationDialogComponent implements OnInit {
  notifications: any[] = [];
  role: string = '';
  email: string = '';

  constructor(
    private authService: AuthService,
    public dialogRef: MatDialogRef<NotificationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any[],
    private router: Router
  ) {}

  ngOnInit(): void {
    this.role = this.authService.getDecodedUser().role;
    this.email = this.authService.getDecodedUser().email;
    console.log(this.email);

    this.notifications = this.data;
  }

  closeDialog(): void {
    this.dialogRef.close();
  }

  shouldDisplayNotification(notification: any): boolean {
    const role = this.authService.getDecodedUser().role;

    if (role === 'CUSTOMER' && !notification.seenByUser && notification.watch.availableStatus) {
      
      return true;
    }

    if (role === 'ADMIN' && !notification.seenByAdmin) {
      return true;
    }

    return false;
  }

  navigateToProductDetail(modelNumber: string): void {
    this.closeDialog();
    this.router.navigateByUrl('/product-detail/' + modelNumber);
  }
}


