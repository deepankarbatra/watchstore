import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { UserdetailsService } from '../services/userdetails.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any;
  email: string=""

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private authService: AuthService,
    private UserService: UserdetailsService,
    private router: Router
  ) { }

  ngOnInit() {
    this.email=this.authService.getDecodedUser().email;
    this.getUser(this.email)

  }
  goToOrders():void{
    this.router.navigateByUrl("orders");
  }

  routeToAddresses():void {
    this.router.navigate(['/addresses']);
  }

  getUser(email:string) {
   // const email = 'Vishal@gmail.com'; // Replace with the actual email or obtain it from somewhere else
    this.UserService.getUserDetails(email).subscribe(
      (response) => {
        this.user = response;
      },
      (error) => {
        console.error('Error fetching user details:', error);
      }
    );
  }


}
