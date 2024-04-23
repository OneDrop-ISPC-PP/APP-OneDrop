import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Importamos ls componentes
import { ServiciosCompComponent } from './servicios-comp/servicios-comp.component';

// Importamos el modulo de router module
import { RouterModule } from '@angular/router';
import { EcommerceRoutingModule } from './ecommerce-routing.module';
import { FormularioPagoComponent } from './formulario-pago/formulario-pago.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ServiciosCompComponent,
    FormularioPagoComponent
  ],
  imports: [
    CommonModule,
    EcommerceRoutingModule,
    ReactiveFormsModule // Agrega esta línea para importar el módulo de formularios reactivos
  ],
  exports:[
    ServiciosCompComponent,
    RouterModule,
    ReactiveFormsModule
  ]
})
export class EcommerceModule { }
