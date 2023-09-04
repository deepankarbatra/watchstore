import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

export const authGuard:  CanActivateFn = (route, state) => {
  const router=inject(Router);
  const authService=inject(AuthService);
  const userRole=authService.getDecodedUser().role;
  if(authService.isAuthenticated()){
    if(route.data['role']&& route.data['role'].indexOf(userRole) === -1){
      router.navigate(['login']);
      return false;
    }
    return true;
  }
  router.navigate(['login']);
  return false;
}; 
