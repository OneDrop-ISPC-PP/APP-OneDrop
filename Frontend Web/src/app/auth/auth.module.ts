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
import { InfoUsuarioDashAdminComponent } from './dashboard-admin/info-usuario-dash-admin/info-usuario-dash-admin.component';
import { InfoServicioDashAdminComponent } from './dashboard-admin/info-servicio-dash-admin/info-servicio-dash-admin.component';
import { BienvenidaDashAdminComponent } from './dashboard-admin/bienvenida-dash-admin/bienvenida-dash-admin.component';

// ANGULAR MATERIAL
// ANGULAR MATERIAL
import {MatCardModule} from '@angular/material/card';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import {MatIconModule} from '@angular/material/icon';
import {MatListModule} from '@angular/material/list'; 
import {MatButtonModule} from '@angular/material/button';
import { ModPacienteAdminComponent } from './mod-paciente-admin/mod-paciente-admin.component';
import { ModNotaPesoUsuarioComponent } from './mod-nota-peso-usuario/mod-nota-peso-usuario.component';
import { ModNotaTensionUsuarioComponent } from './mod-nota-tension-usuario/mod-nota-tension-usuario.component';






@NgModule({
  declarations: [
    LoginComponent,
    RegistroComponent,
    DashboardUsuarioComponent,
    DashboardAdminComponent,
    Registro2usuarioComponent,
    Registro3usuarioComponent,
    ModNotaUsuarioComponent,
    ModServicioAdminComponent,
    InfoUsuarioDashAdminComponent,
    InfoServicioDashAdminComponent,
    BienvenidaDashAdminComponent,
    ModPacienteAdminComponent,
    ModNotaPesoUsuarioComponent,
    ModNotaTensionUsuarioComponent,

  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    MatSlideToggleModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatButtonModule
  ],
  exports:[
    RouterModule,
  ]
})
export class AuthModule { }
