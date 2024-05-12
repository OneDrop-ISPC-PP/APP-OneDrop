import { Injectable } from '@angular/core';
import { LoginService } from './login-service';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot, UrlTree , Router } from '@angular/router';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserGuard implements CanActivate {

  constructor(private loginService:LoginService,private router:Router){

  }


  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      if(this.loginService.verificacionLogeo() && this.loginService.getUserRole() == 'USUARIO'){
        return true;
      }

      this.router.navigateByUrl("/auth/login")
      return false;
  }
  
}
