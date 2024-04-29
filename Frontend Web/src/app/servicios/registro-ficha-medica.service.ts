import { Injectable } from '@angular/core';
import { loginInterface } from './interfaces/loginInterface';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';


@Injectable({
  providedIn: 'root'
})
export class RegistroFichaMedicaService {

  constructor( private http:HttpClient) { }

///////////////////   REGISTRO FICHA MEDICA /////////////////////////////
  POST_REG_FICHA_MEDICA(urlPOST:string, body: any){
    return this.http.post(urlPOST,body)
  }   


}
