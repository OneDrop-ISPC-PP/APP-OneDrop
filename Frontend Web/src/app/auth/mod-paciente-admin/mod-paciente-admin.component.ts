import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router,Params } from '@angular/router';
import { EstadisAdminsService } from 'src/app/servicios/estadis-admins.service';

@Component({
  selector: 'app-mod-paciente-admin',
  templateUrl: './mod-paciente-admin.component.html',
  styleUrls: ['./mod-paciente-admin.component.css']
})
export class ModPacienteAdminComponent implements OnInit{

  dataid!: number;
  paciente:any;
// CONSTRUCTOR 
constructor(
    private routerAct: ActivatedRoute,
    private router: Router,
    private serv_estadistica:EstadisAdminsService

  ){}


// INICIA ON INIT
ngOnInit(): void {
  this.routerAct.paramMap.subscribe((param:Params) =>{
    this.dataid=param['get']("id");
    console.log("El id del paciente es:"+this.dataid);

  })

  this.serv_estadistica.GET_DATOS_PACIENTES(this.dataid).subscribe(
    (data)=>{
      this.paciente=data;
      console.log("los datos del paciente son:");
      console.log(data)
    },
    (error)=>{
      console.log("No se pudieron cargar los datos del paciente");
      console.log(error)

    }


)



}// CIERRA ON INIT
update(){
  this.serv_estadistica.UPDATE_PACIENTE(this.paciente,this.dataid).subscribe(()=>{
    this.router.navigate(["auth/dash_admin/info_paciente"])

  })
}


}
