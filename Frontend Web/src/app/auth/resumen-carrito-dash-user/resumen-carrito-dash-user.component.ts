import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router,Params } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';

@Component({
  selector: 'app-resumen-carrito-dash-user',
  templateUrl: './resumen-carrito-dash-user.component.html',
  styleUrls: ['./resumen-carrito-dash-user.component.css']
})
export class ResumenCarritoDashUserComponent implements OnInit {
  dataid!: number;
  nota:any;

  infoUser:any
  infoCompra:any[] = []
  infoMontos:number[] = []
  monto:number=0


//////////////////////////////////////////////////////////////////
  constructor(
    private routerAct: ActivatedRoute,
    private router: Router,
    private serv_estadistica:EstadisUsuariosService

  ){}

//////////////////////////////////////////////////////////////////
  ngOnInit(): void {

    this.routerAct.paramMap.subscribe((param:Params) =>{
      this.dataid=param['get']("id");
      console.log("El id del carrito es:"+this.dataid);

    })

    this.getCarrito()
    this.infoCompra
    this.infoUser
    this.infoMontos


  } // CIERRA ON INIT

  getCarrito(){
  this.serv_estadistica.getCarritoPorIdCarrito(this.dataid).subscribe(
    (data:any)=>{
      console.log("los datos del carrito son:");
      console.log(data);

      this.infoUser = data.paciente
      this.infoCompra = data.servicios

      let cant = data.servicios.length

      console.log(cant);
      for(let i = 0; i<cant;i++){
          this.infoMontos.push(data.servicios[i].precio)
          this.monto += data.servicios[i].precio
      }

      console.log("los precios son")
      console.log(this.infoMontos)
      console.log("El monto total es:")
      console.log(this.monto)



    },
    (error)=>{
      console.log("No se pudieron los datos del carrito");
      console.log(error)
    
    })

  }
  ///////////////////////////////////////////////////////////////
  
  volver(){
    this.router.navigate(["auth/dash_user/servicios_user"])
  }



}
 
