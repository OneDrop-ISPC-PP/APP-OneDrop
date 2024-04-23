import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { userInterface } from './interfaces/userInterface';
import { loginInterface } from './interfaces/loginInterface';
@Injectable({
  providedIn: 'root'
})
export class FormContactoService {

  constructor( private http:HttpClient ) { }


  email_string:string=""
  

  // SE CREA METODO DE LOGUEO Y CONTROL QUE CONSUMIRA EL LOGIN COMPONENT
      // credenciales trae los datos del form y es de tipo loginInterface.
  login(credenciales:loginInterface):Observable<userInterface>{

    console.log("el email: "+ credenciales.email);

    // OBTENEMOS EL ATRIBUTO email DEL OBSERVABLE Y SE LO CONVIERTE EN STRING
    this.email_string = this.http.get<userInterface>('././assets/data_usuarios.json').pipe(
      map((usuario: userInterface) =>{
        return usuario.email.toString();
      })).toString()

      console.log("??"+this.email_string)
      
      if(this.email_string=credenciales.email){
      }
      return  this.http.get<userInterface>('././assets/data_usuarios.json')
  }


   // METODO POST DE FORMULARIO //////////
   // METODO POST DE FORMULARIO /////////
   // METODO POST DE FORMULARIO /////////
    public LOGIN_POST(url:string,cuerpo: { email: any; pass: any; }){ 
      
      // PASAMOS COMO PARAMTRO EL ENDPOINT (URL) AL CUAL ENVIAMOS LA INFORMACION
      // Y COMO SEGUNDO EL OBJETO QUE VA A CONTENER LA INFORMACION (BODY)
      return this.http.post(url,cuerpo);

    }
  // CIERRA EXPORT
  }

