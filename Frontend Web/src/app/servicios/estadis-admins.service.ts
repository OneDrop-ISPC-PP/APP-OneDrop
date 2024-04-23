import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class EstadisAdminsService {
  
  constructor(
    private http:HttpClient
    ) { }
  //////////   URLS  --  solo hay que descomentar una y comentar la otra  //////////
  //////////   URLS  --  solo hay que descomentar una y comentar la otra  //////////
  //////////   URLS  --  solo hay que descomentar una y comentar la otra  //////////
    
  // URL DE SERVICIOS:
  //url_SERVICIOS:string="http://localhost:8000/api/admin/servicios/";
  url_SERVICIOS:string="http://localhost:3000/SERVICIOS/";

  // URL DE PACIENTES:
  //url_PACIENTES:string='http://localhost:8000/api/paciente/'
  url_PACIENTES:string="http://localhost:3000/REGISTRO_DATOS_PERSONALES/"

  // URL GENERAL DEL FRONT (TRANQUI DAVID):
  url:string="http://localhost:3000/"

  

  // METODO QUE MUESTRA USUARIOS
 muestraUsuarios( ){
   return this.http.get(this.url_PACIENTES, {withCredentials: true})
  }
    
  // METODO QUE MUESTRA LOS SERVICIOS 
  muestraServicios( ){
    return this.http.get(this.url_SERVICIOS, {withCredentials: true})
  }

  // METODO POST DE SERVICIOS
  POSTRegistroServicio(body: any){
    return this.http.post(this.url_SERVICIOS,body, {withCredentials: true})
  }

  // METODO PARA ELIMINAR SERVICIOS
  DELETE(id:string){
    return this.http.delete(this.url_SERVICIOS+id, {withCredentials: true})
  }

  ///// MODIFICAR SERVICIOS /////
  // metodo para TRAER la informacion
  modificar(id:number){
    return this.http.get(this.url_SERVICIOS+id, {withCredentials: true})
  }

  // metodo para MODIFICAR la informacion
  modificar2(datos:any, id:number){
    return this.http.put(this.url_SERVICIOS+id,datos, {withCredentials: true})
  }


/////////// METODOS QUE MUESTRAN COSAS QUE NO TENEMOS POR AHORA ////////////
/////////// METODOS QUE MUESTRAN COSAS QUE NO TENEMOS POR AHORA ////////////
/////////// METODOS QUE MUESTRAN COSAS QUE NO TENEMOS POR AHORA ////////////

  muestraEstadisticas( ){
    return this.http.get(this.url+"estadisticas_admins", {withCredentials: true})
  }

/////////////////////////////////////
  muestraComorbilidades( ){
    return this.http.get(this.url+"estadisticas_comorbilidades_admins", {withCredentials: true})
  }
  /////////////////////////////////////
  muestraIngresos( ){
    return this.http.get(this.url+"ingresos_admins", {withCredentials: true})
  }

}
