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
            {path:'bienvenida', component:BienvenidaDashAdminComponent},
            {path:'**', redirectTo:'bienvenida'}
            
          ]

        
        },



        {path:'dash_user', component:DashboardUsuarioComponent, canActivate:[UserGuard]},
        {path:'registro2usuario', component:Registro2usuarioComponent},
        {path:'registro3usuario', component:Registro3usuarioComponent},
        {path:'edit_nota/:id', component:ModNotaUsuarioComponent},
        {path:'edit_servicio/:id', component:ModServicioAdminComponent},



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
