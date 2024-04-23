import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Importamos componentes
import { InicioComponent } from './inicio/inicio.component';
import { SoporteComponent } from './soporte/soporte.component';
import { NosotrosComponent } from './nosotros/nosotros.component';
import { BlogComponent } from './blog/blog.component';
import { ContactoComponent } from './contacto/contacto.component';

// Importamos modulo de ruteo
import { PagesRoutingModule } from './pages-routing.module';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';




@NgModule({
  declarations: [
    InicioComponent,
    SoporteComponent,
    NosotrosComponent,
    BlogComponent,
    ContactoComponent
  ],
  imports: [
    CommonModule,
    PagesRoutingModule,
    ReactiveFormsModule
  ],
  exports:[
    InicioComponent,
    SoporteComponent,
    NosotrosComponent,
    BlogComponent,
    ContactoComponent,
    RouterModule
  ]
})
export class PagesModule { }
