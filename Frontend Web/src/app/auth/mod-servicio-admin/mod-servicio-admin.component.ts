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

    this.serv_estadistica.modificar(this.dataid).subscribe((data)=>{
      this.servicio=data;

    })

    
  }
///////////////////////////////////////////////////////////////

  update(){
    this.serv_estadistica.modificar2(this.servicio,this.dataid).subscribe(()=>{
      this.router.navigate(["auth/dash_admin"])

    })


  }







}
