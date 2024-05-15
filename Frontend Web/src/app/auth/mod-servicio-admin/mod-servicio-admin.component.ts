import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router,Params } from '@angular/router';
import { EstadisAdminsService } from 'src/app/servicios/estadis-admins.service';

@Component({
  selector: 'app-mod-servicio-admin',
  templateUrl: './mod-servicio-admin.component.html',
  styleUrls: ['./mod-servicio-admin.component.css']
})
export class ModServicioAdminComponent implements OnInit {
  dataid!: number;
  servicio:any;


//////////////////////////////////////////////////////
  constructor(
    private routerAct: ActivatedRoute,
    private router: Router,
    private serv_estadistica:EstadisAdminsService

  ){}


/////////////////////////////////////////////////////
  ngOnInit(): void {

    this.routerAct.paramMap.subscribe((param:Params) =>{
      this.dataid=param['get']("id");
      console.log("El id es:"+this.dataid);

    })

    this.serv_estadistica.GET_DATOS_SERVICIOS(this.dataid).subscribe(
      (data)=>{
        this.servicio=data;
        console.log("los datos del servicio son:");
        console.log(data)
      },
      (error)=>{
        console.log("No se pudieron cargar los datos del servicio");
        console.log(error)
      
      })
  
  }
///////////////////////////////////////////////////////////////

  update(){
    this.serv_estadistica.UPDATE_SERVICIO(this.servicio,this.dataid).subscribe(()=>{
      this.router.navigate(["auth/dash_admin/info_services"])

    })
  }


///////////////////////////////////////////////////////////////////





}
