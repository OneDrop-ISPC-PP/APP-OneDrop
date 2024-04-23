import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Importamos modulos
import { InicioComponent } from './inicio/inicio.component';
import { BlogComponent } from './blog/blog.component';
import { ContactoComponent } from './contacto/contacto.component';
import { NosotrosComponent } from './nosotros/nosotros.component';
import { SoporteComponent } from './soporte/soporte.component';

// Iportamos Router Module
import { RouterModule, Routes } from '@angular/router';


const routes:Routes=[
  {path:'',
    children:[
        {path:'', component:InicioComponent},
        {path:'home', component:InicioComponent},
        {path:'blog', component:BlogComponent},
        {path:'contacto', component:ContactoComponent},
        {path:'nosotros', component:NosotrosComponent},
        {path:'soporte', component:SoporteComponent},
        {path:'**', redirectTo:'inicio'}
    ]
}]


@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)


  ]
})
export class PagesRoutingModule { }
