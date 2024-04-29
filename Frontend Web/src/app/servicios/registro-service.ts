import { Injectable } from '@angular/core';
import { loginInterface } from './interfaces/loginInterface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { userInterface } from './interfaces/userInterface';
import { Token } from '@angular/compiler';


// LA IDEA DE ESTE SERVICIO ESTA VERIFICAR QUE EL EMAIL INGRESADO COINCIDA CON ALGUNO DE NUESTRA
// "BASE DE DATOS" Y EN EL CASO DE QUE SI QUE TIRE UN MENSAJE VE VALIDACION CORRERCTA. PERO POR AHORA NO PUDIMOS COMPLETARLO

@Injectable({
  providedIn: 'root'
})
export class RegistroService {
 

  constructor( private http:HttpClient ) { }
  email_string:string=""

///////////////////   REGISTRO INICIAL OOKK /////////////////////////////
POST_REG_INICIAL( body: any){
  return this.http.post('http://localhost:8080/auth/register',body)
}

// CUANDO NOS REGISTRAMOS Y ESTABLECEMOS EL TOKEN EN EL LOCAL STORAGE
public loginUser(token:any){
  localStorage.setItem('token',token);
}

// COMPROBACION DE ESTADO DEL TOKEN (SI ESTA VENCIDO O NO)
public verificacionLogeo(){
  let tokenStr = localStorage.getItem('token');
  if(tokenStr == undefined || tokenStr == '' || tokenStr == null){
    console.log("El TOKEN esta vencido")
    return false
  }else{
    console.log("El TOKEN sigue vigente")
    return true
  }
}

// OBTENCION DEL TOKEN
public getToken(){
return localStorage.getItem('token')
}

// SET USER 
public setUser(user:any){
  //                          Convierte un valor de JS a un JASON
  localStorage.setItem('user',JSON.stringify(user));
}

// GET USER
public getUser(){
  let userStr = localStorage.getItem('user');
  if(userStr != null){
    // Si no es nulo lo retorna
    return JSON.parse(userStr);
  }else{
    // en el caso contrario cierra sesion
    this.logout();
    return null
  }
}

// CAPTURA EL ROLE DEL USUARIO
public getUserRole(){
  let user = this.getUser();
  return user.authorities[0].authority;
}

// 

/*public getCurrentUser(){
    return this.http.get('http://localhost:8080/auth/actual-usuario')
}
*/



// CIERRE DE SESION Y ELIMINAMOS EL TOKEN DEL LOCAL STORAGE
public logout(){
  console.log("Se elimino el TOKEN");
  localStorage.removeItem('token');
  localStorage.removeItem('user');
  return true
}







}

