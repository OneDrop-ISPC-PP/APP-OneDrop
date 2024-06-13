import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegistroComponent } from './registro/registro.component';
import { RouterModule , Routes} from '@angular/router';
import { DashboardAdminComponent } from './dashboard-admin/dashboard-admin.component';
import { DashboardUsuarioComponent } from './dashboard-usuario/dashboard-usuario.component';
import { Registro2usuarioComponent } from './registro2usuario/registro2usuario.component';
import { Registro3usuarioComponent } from './registro3usuario/registro3usuario.component';
import { ModNotaUsuarioComponent } from './mod-nota-usuario/mod-nota-usuario.component';
import { ModServicioAdminComponent } from './mod-servicio-admin/mod-servicio-admin.component';
import { AdminGuard } from '../servicios/admin.guard';
import { UserGuard } from '../servicios/user.guard';
import { InfoServicioDashAdminComponent } from './dashboard-admin/info-servicio-dash-admin/info-servicio-dash-admin.component';
import { InfoUsuarioDashAdminComponent } from './dashboard-admin/info-usuario-dash-admin/info-usuario-dash-admin.component';
import { BienvenidaDashAdminComponent } from './dashboard-admin/bienvenida-dash-admin/bienvenida-dash-admin.component';
import { ModPacienteAdminComponent } from './mod-paciente-admin/mod-paciente-admin.component';
import { ModNotaPesoUsuarioComponent } from './mod-nota-peso-usuario/mod-nota-peso-usuario.component';
import { ModNotaTensionUsuarioComponent } from './mod-nota-tension-usuario/mod-nota-tension-usuario.component';
import { InfoGlucemiaDashUserComponent } from './dashboard-usuario/info-glucemia-dash-user/info-glucemia-dash-user.component';
import { InfoPesoDashUserComponent } from './dashboard-usuario/info-peso-dash-user/info-peso-dash-user.component';
import { BienvenidaDashUserComponent } from './dashboard-usuario/bienvenida-dash-user/bienvenida-dash-user.component';
import { InfoTensionDashUserComponent } from './dashboard-usuario/info-tension-dash-user/info-tension-dash-user.component';
import { ServiciosDashUserComponent } from './dashboard-usuario/servicios-dash-user/servicios-dash-user.component';
import { MasInfoServicioDashUserComponent } from './mas-info-servicio-dash-user/mas-info-servicio-dash-user.component';
import { Resumen2CarritoUserComponent } from './resumen2-carrito-user/resumen2-carrito-user.component';
Resumen2CarritoUserComponent






// RUTAS EXPORTADAS AL NAV
const routes:Routes=[
  {path:'',
    children:[
        {path:'login', component:LoginComponent},
        {path:'registrarse', component:RegistroComponent},
        

        {path:'dash_admin',
         component:DashboardAdminComponent,
         canActivate:[AdminGuard],
         children:[
            {path:'info_users', component:InfoUsuarioDashAdminComponent},
            {path:'info_services', component:InfoServicioDashAdminComponent},
            {path:'bienvenida_admin', component:BienvenidaDashAdminComponent},
            {path:'**', redirectTo:'info_users'}
            
          ]
        },

        {path:'dash_user', component:DashboardUsuarioComponent, canActivate:[UserGuard],
        children:[
          {path:'bienvenida_user', component:BienvenidaDashUserComponent},
          {path:'info_glucemia', component:InfoGlucemiaDashUserComponent},
          {path:'info_peso', component:InfoPesoDashUserComponent},
          {path:'info_tension', component:InfoTensionDashUserComponent},
          {path:'servicios_user', component:ServiciosDashUserComponent},

          {path:'**', redirectTo:'info_glucemia'}
        ]
        },
        {path:'registro2usuario', component:Registro2usuarioComponent},
        {path:'registro3usuario', component:Registro3usuarioComponent},

        {path:'edit_nota_glucemia/:id', component:ModNotaUsuarioComponent},
        {path:'edit_nota_peso/:id', component:ModNotaPesoUsuarioComponent},
        {path:'edit_nota_tension/:id', component:ModNotaTensionUsuarioComponent},
        {path:'edit_servicio/:id', component:ModServicioAdminComponent},
        {path:'edit_paciente/:id', component:ModPacienteAdminComponent},
        {path:'mas_info_serv/:id', component:MasInfoServicioDashUserComponent},
        {path:'resumen2_carrito/:id', component:Resumen2CarritoUserComponent},







       {path:'**', redirectTo:'home'}

    ]
}
]

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class AuthRoutingModule { }
