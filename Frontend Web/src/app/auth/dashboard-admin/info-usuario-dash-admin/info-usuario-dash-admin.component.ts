import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EstadisAdminsService } from 'src/app/servicios/estadis-admins.service';

@Component({
  selector: 'app-info-usuario-dash-admin',
  templateUrl: './info-usuario-dash-admin.component.html',
  styleUrls: ['./info-usuario-dash-admin.component.css']
})
export class InfoUsuarioDashAdminComponent implements OnInit {
  // VARIABLES
  listaDePacientes:any = [];

  // CONSTRUCTOR
  constructor(
    private http: HttpClient,
    private estad_admin:EstadisAdminsService,
    private formBuilder:FormBuilder,
  ){}

  // ON INIT
  ngOnInit(): void {
    // SUSCRIPCION A LA LISTA DE SERVICIOS
    this.estad_admin.GET_PACIENTES().subscribe(
      (data:any)=>{
        this.listaDePacientes = data.users;
        console.log("CARGA DE PACIENTES EXITOSA, LOS DATOS SON:")
        console.log(data.users);

      },
      (error:any) => {
        console.log("ERROR EN LA CARGA DE LOS PACIENTES");
        console.log(error);

      })

  }// CIERRA ON INIT


  // METODO DELETE
  darBajaAUsuario(id:string){
    this.estad_admin.DELETE_USUARIO(id).subscribe(
      (data:any)=>{
        alert("Servicio Eliminado")
      },
      (error)=>{
        console.log("ERROR AL ELIMINAR USUARIO");

      })
  }


}
