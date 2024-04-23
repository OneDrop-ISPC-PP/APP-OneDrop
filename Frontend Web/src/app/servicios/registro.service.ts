import { Injectable } from '@angular/core';
import { loginInterface } from './interfaces/loginInterface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { userInterface } from './interfaces/userInterface';


// LA IDEA DE ESTE SERVICIO ESTA VERIFICAR QUE EL EMAIL INGRESADO COINCIDA CON ALGUNO DE NUESTRA
// "BASE DE DATOS" Y EN EL CASO DE QUE SI QUE TIRE UN MENSAJE VE VALIDACION CORRERCTA. PERO POR AHORA NO PUDIMOS COMPLETARLO

@Injectable({
  providedIn: 'root'
})
export class RegistroService {
 

  constructor( private http:HttpClient ) { }


  email_string:string=""
  

  // SE CREA METODO DE LOGUEO Y CONTROL QUE CONSUMIRA EL LOGIN COMPONENT
      // credenciales trae los datos del form y es de tipo loginInterface.
      login(credenciales:any):Observable<any>{
        //No tiene sentido, pero tomamos los datos
        console.log("Estos son los datos que de ingresaron en el formulario")
        console.log("Fijate como hacer la verificacion despues")
        console.log(credenciales)


      // La idea seria que los envie con el metodo POST
        return this.http.post("http://localhost:3000/USUARIOS",credenciales)
    }


   // METODO POST DE FORMULARIO //////////
   // METODO POST DE FORMULARIO /////////
   // METODO POST DE FORMULARIO /////////
    public LOGIN_POST(url:string,cuerpo: { email: any; pass: any; }){ 
      
      // PASAMOS COMO PARAMTRO EL ENDPOINT (URL) AL CUAL ENVIAMOS LA INFORMACION
      // Y COMO SEGUNDO EL OBJETO QUE VA A CONTENER LA INFORMACION (BODY)
      return this.http.post(url,cuerpo);

    }

  /////////////////////////////////////
  //          SERVICIO POST          //
  /////////////////////////////////////

  POSTRegistroUsuario(urlPOST:string, body: any){
    return this.http.post(urlPOST,body)
}










  // CIERRA EXPORT


  }

