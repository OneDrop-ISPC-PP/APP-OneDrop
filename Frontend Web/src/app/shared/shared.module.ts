import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { FooterComponent } from './footer/footer.component';
import { Router, RouterModule } from '@angular/router';
import { PagesModule } from '../pages/pages.module';
import { InicioComponent } from '../pages/inicio/inicio.component';
import { ScrollBackToTopButtonComponent } from './scroll-back-to-top-button/scroll-back-to-top-button.component';

// Importamos los componentes de pages


//const routes:Router[]



@NgModule({
  declarations: [
    NavComponent,
    FooterComponent,
    ScrollBackToTopButtonComponent
  ],
  imports: [
    CommonModule,
  //  RouterModule.forRoot( routes )
  ],
  exports:[
    NavComponent,
    FooterComponent,
    RouterModule,
    ScrollBackToTopButtonComponent

  ]
})
export class SharedModule { }
