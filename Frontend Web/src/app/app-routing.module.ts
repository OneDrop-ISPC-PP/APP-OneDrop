import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


// Importamos router module
import { RouterModule, Routes } from '@angular/router';



const routes: Routes = [
    {path:'',
      loadChildren:() => import('./pages/pages.module').then(m=>m.PagesModule)  
  },
  {path:'auth',
    loadChildren:() => import('./auth/auth.module').then(m => m.AuthModule)
  },
  {path:'ecommerce',
    loadChildren:() => import('./ecommerce/ecommerce.module').then(m => m.EcommerceModule)
  }
];

@NgModule({
  imports: [
  CommonModule,
  RouterModule.forRoot(routes)
],

  exports: [RouterModule]
})
export class AppRoutingModule { }
