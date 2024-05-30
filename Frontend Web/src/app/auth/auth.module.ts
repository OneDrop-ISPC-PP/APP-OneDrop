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
import { BienvenidaDashUserComponent } from './dashboard-usuario/bienvenida-dash-user/bienvenida-dash-user.component';
import { InfoGlucemiaDashUserComponent } from './dashboard-usuario/info-glucemia-dash-user/info-glucemia-dash-user.component';
import { InfoPesoDashUserComponent } from './dashboard-usuario/info-peso-dash-user/info-peso-dash-user.component';
import { InfoTensionDashUserComponent } from './dashboard-usuario/info-tension-dash-user/info-tension-dash-user.component';
import { ServiciosDashUserComponent } from './dashboard-usuario/servicios-dash-user/servicios-dash-user.component';
import { MasInfoServicioDashUserComponent } from './dashboard-usuario/mas-info-servicio-dash-user/mas-info-servicio-dash-user.component';
import { ResumenCarritoDashUserComponent } from './dashboard-usuario/resumen-carrito-dash-user/resumen-carrito-dash-user.component';






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
    BienvenidaDashUserComponent,
    InfoGlucemiaDashUserComponent,
    InfoPesoDashUserComponent,
    InfoTensionDashUserComponent,
    ServiciosDashUserComponent,
    MasInfoServicioDashUserComponent,
    ResumenCarritoDashUserComponent,

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
