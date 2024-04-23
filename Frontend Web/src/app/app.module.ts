import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';

//Modulo para el ruteo
import { AppRoutingModule } from './app-routing.module';
//Importamos modulos
import { SharedModule } from './shared/shared.module';
import { PagesModule } from './pages/pages.module';
import { EcommerceModule } from './ecommerce/ecommerce.module';
import { HttpClientModule } from '@angular/common/http'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { Router, RouterModule } from '@angular/router';
import { EcommerceServiceService } from './servicios/ecommerce-service.service';
import { LoginComponent } from './auth/login/login.component';
import { RegistroComponent } from './auth/registro/registro.component';
import { FormContactoService } from './servicios/form-contacto.service';




// instalado en pkg json, y agregado a providers por david
import { CookieService } from "ngx-cookie-service";
import { AuthService } from './servicios/auth.service';
import { authInterceptorProviders } from './servicios/interceptors/auth.interceptor';


@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    PagesModule,
    EcommerceModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [CookieService,authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }

