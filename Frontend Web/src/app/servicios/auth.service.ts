import { Injectable } from '@angular/core';
import { loginInterface } from './interfaces/loginInterface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor( private http:HttpClient, private cookies: CookieService ) { }
//////////////////////////////////////////////////////////////
///////////////////   REGISTRO   /////////////////////////////
///////////////////   REGISTRO   /////////////////////////////
//////////////////////////////////////////////////////////////

  POST(urlPOST:string, body: any){
    return this.http.post(urlPOST,body, {withCredentials: true})
}


//////////////////////////////////////////////////////////////
/////////// SEGUNDO FORMULARIO DE  REGISTRO   ////////////////
/////////// SEGUNDO FORMULARIO DE  REGISTRO   ////////////////
//////////////////////////////////////////////////////////////






//////////////////////////////////////////////////////////////
///////////////////   LOGIN   ////////////////////////////////
///////////////////   LOGIN   ////////////////////////////////
//////////////////////////////////////////////////////////////

  



/////////////////////// CODIGO EN DUDA  /////////////////////////
///////////////////////  NO SERVIRIA    /////////////////////////
/////////////////////// CODIGO EN DUDA  /////////////////////////
/////////////////////// CODIGO EN DUDA  /////////////////////////



/////////////////////// REGISTRO  /////////////////////////

  // SE CREA METODO DE LOGUEO Y CONTROL QUE CONSUMIRA EL LOGIN COMPONENT
      // credenciales trae los datos del form y es de tipo loginInterface.
      signin(credenciales:any):Observable<any>{
        //No tiene sentido, pero tomamos los datos
        console.log("Estos son los datos que de ingresaron en el formulario")
        console.log("Fijate como hacer la verificacion despues")
        console.log(credenciales)


      // La idea seria que los envie con el metodo POST
        return this.http.post("http://localhost:3000/USUARIOS",credenciales)
    }


   // METODO POST DE FORMULARIO //////////

    public LOGIN_POST(url:string,cuerpo: { email: any; pass: any; }){ 
      
      // PASAMOS COMO PARAMTRO EL ENDPOINT (URL) AL CUAL ENVIAMOS LA INFORMACION
      // Y COMO SEGUNDO EL OBJETO QUE VA A CONTENER LA INFORMACION (BODY)
      return this.http.post(url,cuerpo);

    }



/////////////////////// LOGIN /////////////////////////

// SE CREA METODO DE LOGUEO Y CONTROL QUE CONSUMIRA EL LOGIN COMPONENT
login(credenciales:loginInterface):Observable<any>{

  const {email, pass} = credenciales;
  const bodyyy = {email: email, password : pass}
  const url = 'http://127.0.0.1:8000/api/auth/login/'
  // return this.http.get("http://localhost:3000/USUARIOS")

  return this.http.post(url,bodyyy)
}

url:string='http://127.0.0.1:8000/api/paciente/servicios/';

muestraservicioausuario( ){

return this.http.get(this.url)
}


// agregado por david set y get token
setToken(token: string) { // antes era String ...
this.cookies.set("csrftoken", token);
}
getToken() {
return this.cookies.get("csrftoken");
}













  }
