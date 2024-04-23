import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ServiciosCompComponent } from './servicios-comp/servicios-comp.component';
import { FormularioPagoComponent } from './formulario-pago/formulario-pago.component';

import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'servicios', component: ServiciosCompComponent },
  { path: 'formulario_pago', component: FormularioPagoComponent },
  { path: '**', redirectTo: './auth/dashuser' }
];

@NgModule({
  imports: [CommonModule, RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EcommerceRoutingModule {}
