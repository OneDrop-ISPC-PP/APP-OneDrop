import { Injectable } from '@angular/core';
import { loginInterface } from './interfaces/loginInterface';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { BACKEND_URL } from '../app.config';


import { userInterface } from './interfaces/userInterface';

// agregado por david
import { CookieService } from 'ngx-cookie-service';


// LA IDEA DE ESTE SERVICIO ESTA VERIFICAR QUE EL EMAIL INGRESADO COINCIDA CON ALGUNO DE NUESTRA
// "BASE DE DATOS" Y EN EL CASO DE QUE SI QUE TIRE UN MENSAJE VE VALIDACION CORRERCTA. PERO POR AHORA NO PUDIMOS COMPLETARLO

@Injectable({
  providedIn: 'root'
})
export class LoginService {
 

  constructor( private http:HttpClient, private router:Router) { }


  email_string:string=""

/*
  // agregado por david set y get token
  setToken(token: string) { // antes era String ...
    this.cookies.set("csrftoken", token);
  }
  getToken2() {
    return this.cookies.get("csrftoken");
  }

*/

public loginStatusSubject = new Subject<boolean>();

///////////////////   REGISTRO LOGIN OOKK /////////////////////////////
  POST_LOGIN(body: any){
    return this.http.post(BACKEND_URL+'/auth/login',body)
  } 

// CUANDO NOS LOGEAMOS Y ESTABLECEMOS EL TOKEN EN EL LOCAL STORAGE
public loginUser(token:any){
  localStorage.setItem('token',token);
}

// COMPROBACION DE ESTADO DEL TOKEN (SI ESTA VENCIDO O NO)
public verificacionLogeo(){
  let tokenStr = localStorage.getItem('token');
  if(tokenStr == undefined || tokenStr == '' || tokenStr == null){
    //console.log("El TOKEN esta vencido")
    return false
  }else{
    //console.log("El TOKEN está vigente")
    return true
  }
}

// REDIRECCION DEL BOTON DEL USERNAME
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

// VERIFICACION DE SI ES ADMIN
  public verificacionAdmin(){
    let user = this.getUser();
    if(user.role == "ADMIN"){
      return true;
    }else{
      return false
    }
  }

// VERIFICACION DE SI ES MEDICO
public verificacionMedico(){
  let user = this.getUser();
  if(user.role == "MEDICO"){
    return true;
  }else{
    return false
  }
}

// VERIFICACION DE SI ES PACIENTE
public verificacionPaciente(){
  let user = this.getUser();
  if(user.role == "USUARIO"){
    return true;
  }else{
    return false
  }
}

// OBTENCION DEL TOKEN
public getToken(){
return localStorage.getItem('token' );
}

// SET USER - LO ESTABLECEMOS EN EL LOCAL STORAGE
public setUser(user:any){
  //                          Convierte un valor de JS a un JASON
  localStorage.setItem('user',JSON.stringify(user));
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

// CAPTURA EL ROLE DEL USUARIO
public getUserRole(){
  let user = this.getUser();
  return user.role;

}

// CAPTURA EL ID DEL USUARIO
public getUserId(){
  let user = this.getUser();
  return user.id;
}

// CAPTURA EL NOMBRE DEL USUARIO
public getNameUser(){
  let user = this.getUser();
  return user.nombre;
}

// CAPTURA EL APELLIDO DEL USUARIO
public getSurnameUser(){
  let user = this.getUser();
  return user.apellido;
}



// GET DE USUARIOS ACTUAL (CONSULTAR EL FINAL DE LA RUTA) // NO FUNCIONA
public getCurrentUser(){
    return this.http.get(BACKEND_URL+'/auth/userDetails')
}




// CIERRE DE SESION Y ELIMINAMOS EL TOKEN DEL LOCAL STORAGE
public logout(){
  console.log("Se elimino el TOKEN luego del registro inicial");
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  return true
}













///////////////////////////////////////////////
///////////////////////////////////////////////
  /*

  // SE CREA METODO DE LOGUEO Y CONTROL QUE CONSUMIRA EL LOGIN COMPONENT
      // credenciales trae los datos del form y es de tipo loginInterface.
  login(credenciales:loginInterface):Observable<any>{

        const {email, pass} = credenciales;
        const bodyyy = {email: email, password : pass}
        const url = 'http://127.0.0.1:8000/api/auth/login/'
        // return this.http.get("http://localhost:3000/USUARIOS")
        const loguedUser = this.http.post(url,bodyyy)
        return loguedUser
    }

    url:string='http://127.0.0.1:8000/api/paciente/servicios/';
    muestraservicioausuario( ){

      return this.http.get(this.url)
    }
  // CIERRA EXPORT*/

  }



