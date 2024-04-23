import { Injectable } from '@angular/core';
import { loginInterface } from './interfaces/loginInterface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { userInterface } from './interfaces/userInterface';

// agregado por david
import { CookieService } from 'ngx-cookie-service';


// LA IDEA DE ESTE SERVICIO ESTA VERIFICAR QUE EL EMAIL INGRESADO COINCIDA CON ALGUNO DE NUESTRA
// "BASE DE DATOS" Y EN EL CASO DE QUE SI QUE TIRE UN MENSAJE VE VALIDACION CORRERCTA. PERO POR AHORA NO PUDIMOS COMPLETARLO

@Injectable({
  providedIn: 'root'
})
export class LoginService {
 

  constructor( private http:HttpClient, private cookies: CookieService ) { }


  email_string:string=""
  

  // agregado por david set y get token
  setToken(token: string) { // antes era String ...
    this.cookies.set("csrftoken", token);
  }
  getToken() {
    return this.cookies.get("csrftoken");
  }

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
  // CIERRA EXPORT
  }



