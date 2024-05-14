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
  url_SERVICIOS:string="http://localhost:8080/servicios/";
  //url_SERVICIOS:string="http://localhost:3000/SERVICIOS/";

  // URL DE PACIENTES:
  url_USUARIOS:string='http://localhost:8080/auth/users';
  url_PACIENTES:string='http://localhost:8080/auth/users/pacientes';
  url_MEDICOS:string='http://localhost:8080/auth/users/medicos';


  //url_PACIENTES:string="http://localhost:3000/REGISTRO_DATOS_PERSONALES/"

  // URL GENERAL DEL FRONT (TRANQUI DAVID):
  url:string="http://localhost:3000/"



/// CODIGO DE SERVICIOS 2024 ///
/// CODIGO DE SERVICIOS 2024 ///
/// CODIGO DE SERVICIOS 2024 ///

// SERVICIOS DASHBOARD ADMIN //
// SERVICIOS DASHBOARD ADMIN //

  // METODO POST DE SERVICIOS
  public POST(body: any){
    return this.http.post(this.url_SERVICIOS,body)
  }
  // METODO PARA LISTAR SERVICIOS
  public GET_SERVICIOS(){
    return this.http.get(this.url_SERVICIOS)
  }
    // METODO PARA ELIMINAR SERVICIOS
  public DELETE_SERVICIO(id:string){
    return this.http.delete(this.url_SERVICIOS+id, {withCredentials: true})
  }


// MODIFICACION SERVICIOS DASHBOARD ADMIN //
// MODIFICACION SERVICIOS DASHBOARD ADMIN //

  // metodo para TRAER la informacion
  public GET_DATOS(id:number){
    return this.http.get(this.url_SERVICIOS+id, {withCredentials: true})
  }
  // metodo para MODIFICAR la informacion
  public UPDATE(datos:any, id:number){
    return this.http.put(this.url_SERVICIOS+id,datos, {withCredentials: true})
  }


// USUARIOS DASHBOARD ADMIN //
// USUARIOS DASHBOARD ADMIN //
public GET_USUARIOS(){
  return this.http.get(this.url_PACIENTES);
}
// METODO PARA ELIMINAR SERVICIOS
// METODO PARA ELIMINAR SERVICIOS
public DELETE_USUARIO(id:string){
  return this.http.delete(this.url_PACIENTES+id, {withCredentials: true})
  
}




// MODIFICACION USUARIOS DASHBOARD ADMIN //
// MODIFICACION USUARIOS DASHBOARD ADMIN //





///////////////////////////////////////////////////
///////////////////////////////////////////////////
///////////////////////////////////////////////////

  // METODO QUE MUESTRA USUARIOS
 muestraUsuarios( ){
   return this.http.get(this.url_USUARIOS, {withCredentials: true})
  }
    
  // METODO QUE MUESTRA LOS SERVICIOS 
  muestraServicios( ){
    return this.http.get(this.url_SERVICIOS, {withCredentials: true})
  }

  // METODO POST DE SERVICIOS
  POSTRegistroServicio(body: any){
    return this.http.post(this.url_SERVICIOS,body, {withCredentials: true})
  }



  ///// MODIFICAR SERVICIOS /////



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
