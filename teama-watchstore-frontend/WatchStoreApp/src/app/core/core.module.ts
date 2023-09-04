import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button'; 
import { HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from '../app-routing.module';
import { NotificationDialogComponent } from './notification-dialog/notification-dialog.component';
import { ProfileComponent } from './profile/profile.component';
import { MatCardModule } from '@angular/material/card';
import { LoadingComponent } from './loading/loading.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';







@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent,
    NotificationDialogComponent,
    ProfileComponent,
    LoadingComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    HttpClientModule,
    MatCardModule,
    MatSnackBarModule,
    MatButtonModule,
    MatDialogModule,
    AppRoutingModule,
    MatIconModule,
    MatProgressBarModule
  ],
  exports: [
    RegisterComponent,
    NotificationDialogComponent,
    ProfileComponent,
    LoadingComponent
  ]
})
export class CoreModule { }
