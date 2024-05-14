import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EcommerceServiceService {
  
  url:string="http://localhost:8080/servicios/";

  //url:string='http://127.0.0.1:8000/api/paciente/servicios/';  ENDPOINT PARA EL BACK

  ////////////////////////////////////////////////////
  constructor(
    private http:HttpClient ) { }

  
  ///////////////////////////////////////////////////
  // metodo GET para traer los servicios
  GET_SERVICIOS( ){
    return this.http.get(this.url)
  }

}

