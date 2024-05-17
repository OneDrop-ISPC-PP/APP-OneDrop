import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Emitters } from './emitters/emitters';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/servicios/login-service';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'onedrop-app';
  //authenticated = false;
  message: string="";


//////////////////////////////////////////////////
  constructor(private http: HttpClient, public login:LoginService,private router:Router) {}

  ngOnInit(): void {}
  

  public iniciarSesion(){
    this.router.navigateByUrl("/authLogin")

  }
  public cerrarSesion(){
    this.login.logout();
    this.router.navigateByUrl("/home")

  }

// GET USER
public getUser(){
  let userStr = localStorage.getItem('user');
  if(userStr != null){
    // Si no es nulo lo retorna
    //console.log("El usuario STR es:")
    //console.log(userStr)

    return JSON.parse(userStr);
  }else{
    // en el caso contrario cierra sesion
    //this.logout();
    return null
  }
}

public redireccionUsername(){
  let user = this.getUser();
  if(user.role == "ADMIN"){
    this.router.navigateByUrl("auth/dash_admin");
  }
  if(user.role == "MEDICO"){
    this.router.navigateByUrl("auth/dash_admin");
  }
  if(user.role == "USUARIO"){
  this.router.navigateByUrl("auth/dash_user");
  }

}


/*
////////////////////////////////////////////////////
  ngOnInit(): void {
    Emitters.authEmitter.subscribe(
      (auth: boolean) => {
        this.authenticated = auth;
      }
    );


    this.http.get('http://localhost:4200/', {withCredentials: true}).subscribe(
      (res: any) => {
        this.message = `Bienvenido`;
        Emitters.authEmitter.emit(true);
      },
    );
  }

  


  /////////////////////////////////////////////////
  logout(): void {
    this.http.post('http://localhost:8000/api/auth/logout/', {}, {withCredentials: true})
      .subscribe(() => this.authenticated = false);
  }

  */
}
