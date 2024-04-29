import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/servicios/login-service';
import { Router } from '@angular/router';



@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit{

  constructor(public login:LoginService,private router:Router){}

  ngOnInit(): void {}

  public iniciarSesion(){
    this.router.navigateByUrl("/authLogin")

  }
  public cerrarSesion(){
    this.login.logout();
    this.router.navigateByUrl("/home")

  }

}
