import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router,Params } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';

@Component({
  selector: 'app-mas-info-servicio-dash-user',
  templateUrl: './mas-info-servicio-dash-user.component.html',
  styleUrls: ['./mas-info-servicio-dash-user.component.css']
})
export class MasInfoServicioDashUserComponent implements OnInit {
  dataid!: number;
  nota:any;

  infoServicio:any;



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

    this.getInfoServicio()



  } // CIERRA ON INIT

  getInfoServicio(){
  this.serv_estadistica.getServicioPorId(this.dataid).subscribe(
    (data:any)=>{
      console.log("los datos del servicio son:");
      console.log(data);

      this.infoServicio = data;

    },
    (error)=>{
      console.log("No se pudieron los datos del servicio");
      console.log(error)
    
    })

  }
  ///////////////////////////////////////////////////////////////
  
  volver(){
    this.router.navigate(["auth/dash_user/servicios_user"])
  }



}
 