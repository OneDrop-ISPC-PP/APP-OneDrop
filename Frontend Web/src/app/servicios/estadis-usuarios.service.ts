import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


// INTERFACE DE NOTAS USUARIO
import { NotasGlucemia } from './interfaces/notas-glucemia';


@Injectable({
  providedIn: 'root'
})
export class EstadisUsuariosService {

  constructor(
    private http:HttpClient) { }

  //////////   URLS  --  solo hay que descomentar una y comentar la otra  //////////
  //////////   URLS  --  solo hay que descomentar una y comentar la otra  //////////
  //////////   URLS  --  solo hay que descomentar una y comentar la otra  //////////
  
  // URL DE NOTAS:
  url_NOTAS:string='http://localhost:8080/registros/glucemia/usuario/';
  //url_NOTAS:string='http://localhost:3000/notas_usuarios/';

  // URL DE SERVICIOS:
  url_SERVICIOS:string='http://localhost:8080/servicios/';
  //url_SERVICIOS:string='http://localhost:3000/SERVICIOS/';

  // URL DE CARRITO:
  //url_CARRITO:string="http://localhost:8000/api/paciente/carrito/";
  url_CARRITO:string="http://localhost:3000/CARRITO/";



  ///////////////////////////////////////////////////
  ////////////////// CODIGO NOTAS ///////////////////
  ///////////////////////////////////////////////////  
  
  //// MUESTRA LAS NOTAS  
  muestraNotasUsuario( ){
    return this.http.get(this.url_NOTAS, {withCredentials: true})
  }

  //// AGREGA NOTAS
  nuevaNota(datos: any){
    return this.http.post(this.url_NOTAS,datos, {withCredentials: true});
  }

  //// MODIFICAR NOTAS
  // metodo para TRAER la informacion
  modificar(id:number){
    return this.http.get(this.url_NOTAS+id , {withCredentials: true})
  }

  // metodo para MODIFICAR la informacion
  modificar2(datos:any, id:number){
    return this.http.put(this.url_NOTAS+id,datos , {withCredentials: true})
  }

  // metodo para ELIMINAR la informacion
  DELETE(id:string){
    return this.http.delete(this.url_NOTAS+id , {withCredentials: true})
  }


  /////////////////////////////////////////////////////////
  ////////////////// CODIGO DEL CARRITO ///////////////////
  /////////////////////////////////////////////////////////

  //// MUESTRA LOS SERVICIOS DISPONIBLES A LOS USUARIOS ////
  muestraServicioAUsuario( ){
    return this.http.get<NotasGlucemia[]>(this.url_SERVICIOS, {withCredentials: true})
  }

  //// MUESTRA CARRITO USUARIOS ////
  muestraCarritoAUsuario(){
    return this.http.get(this.url_CARRITO, {withCredentials: true})
  }

  //// AGREGA SERVICIO AL CARRITO ////
  agregaAlCarrito(servicio:any){
    return this.http.post(this.url_CARRITO,servicio , {withCredentials: true})
  }

  //// ELIMINA SERVICIO AL CARRITO ////
  DELETE_SERV(id:string){
    return this.http.delete(this.url_CARRITO+id , {withCredentials: true})
  }


}
