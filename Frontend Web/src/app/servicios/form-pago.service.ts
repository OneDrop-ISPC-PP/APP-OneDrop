import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FormPagoService {
 

  constructor( private http:HttpClient ) { }
  
  
  url_PAGO:string="http://localhost:3000/datos_pago/"

  /////////////////////////////////////
  //          SERVICIO POST          //
  /////////////////////////////////////

  POSTFormularioPago(body: any){
    return this.http.post(this.url_PAGO,body,{withCredentials: true})
}










  // CIERRA EXPORT


  }
