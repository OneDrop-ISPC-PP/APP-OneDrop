
    /*
    let headersAuth = {}
    const tokenLoguedUser = window.localStorage.getItem("auth_token")
    if(tokenLoguedUser !== null && tokenLoguedUser !== "null"){
        headersAuth = { "Authorization" : `Bearer ${tokenLoguedUser}` }
    }
    
    */

import { HTTP_INTERCEPTORS, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginService } from "../login-service";
import { Observable } from "rxjs";

@Injectable()
export class AuthInterceptor implements HttpInterceptor{

    constructor(private loginService:LoginService ){

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authReq = req;
        const token = this.loginService.getToken();
        if (token != null){
            authReq = authReq.clone({
                setHeaders:{Authorization : `Bearer ${token}`}
            })
        }
        return next.handle(authReq)
    }

}

export const authInterceptorProviders = [
    {
        provide : HTTP_INTERCEPTORS,
        useClass : AuthInterceptor,
        multi : true // para muchos interceptores
    }
]