import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { AuthRoutingModule } from './auth-routing.module';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardUsuarioComponent } from './dashboard-usuario/dashboard-usuario.component';
import { DashboardAdminComponent } from './dashboard-admin/dashboard-admin.component';
import { Registro2usuarioComponent } from './registro2usuario/registro2usuario.component';
import { Registro3usuarioComponent } from './registro3usuario/registro3usuario.component';
import { ModNotaUsuarioComponent } from './mod-nota-usuario/mod-nota-usuario.component';
import { ModServicioAdminComponent } from './mod-servicio-admin/mod-servicio-admin.component';





@NgModule({
  declarations: [
    LoginComponent,
    RegistroComponent,
    DashboardUsuarioComponent,
    DashboardAdminComponent,
    Registro2usuarioComponent,
    Registro3usuarioComponent,
    ModNotaUsuarioComponent,
    ModServicioAdminComponent
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule
  ],
  exports:[
    RouterModule
  ]
})
export class AuthModule { }
