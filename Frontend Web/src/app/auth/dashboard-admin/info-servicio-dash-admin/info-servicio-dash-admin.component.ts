import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EstadisAdminsService } from 'src/app/servicios/estadis-admins.service';

@Component({
  selector: 'app-info-servicio-dash-admin',
  templateUrl: './info-servicio-dash-admin.component.html',
  styleUrls: ['./info-servicio-dash-admin.component.css']
})
export class InfoServicioDashAdminComponent implements OnInit{

  // VARIABLES


  FormRegistroServicios: FormGroup|any;
  listaDeServicios:any = [];


  // CONSTRUCTOR
    constructor(
      private http: HttpClient,
      private estad_admin:EstadisAdminsService,
      private formBuilder:FormBuilder,
    ){}

  // ON INIT
    ngOnInit(): void {
      
    // OBJETO FORMBUILDER
      this.FormRegistroServicios = this.formBuilder.group({

        nombre:["",Validators.required],
        descripcion:["",Validators.required],
        precio:["",Validators.required],
        comentarios:["",Validators.required],
      })

    // SUSCRIPCION A LA LISTA DE SERVICIOS
      this.estad_admin.GET_SERVICIOS().subscribe(
        (data:any)=>{
          this.listaDeServicios = data.registros;
          console.log("CARGA DE SERVICIOS EXITOSA LOS DATOS SON:")
          console.log(data.registros);

        },
        (error) => {
          console.log("ERROR EN LA CARGA DE LOS SERVICIOS")
          console.log(error);

        }

      )

    } //  CIERRA ON INIT

 ///// METODOS GET DEL FORMULARIO/////
 get nombre_servicio_GET(){
  return this.FormRegistroServicios.controls['nombre'];
}
get descripcion_servicio_GET(){
  return this.FormRegistroServicios.controls['descripcion'];
}
get precio_servicio_GET(){
  return this.FormRegistroServicios.controls['precio'];
}
get comentarios_servicio_GET(){
  return this.FormRegistroServicios.controls['comentarios'];
}


// METODO POST DEL REGISTRO DE NUEVO SERVICIO
enviarDatosDeServicio(){

  // SI EL FORMULARIO CUMPLE CON LA VALIDACION
  if(this.FormRegistroServicios.valid){

    // Envia los datos al post
        this.estad_admin.POSTRegistroServicio({
            /*
            EL ID ES AUTOGENERADO POR LA BD, AL REGISTRAR UN SERVICIO NO HACE FALTA MANDARLO, DEBEMOS ELIMINARLO DEL FORM
            id:this.formPOSTRegistroServicio.value.id,
            */
            nombre:this.FormRegistroServicios.value.nombre,
            descripcion:this.FormRegistroServicios.value.descripcion,
            precio:this.FormRegistroServicios.value.precio,
            comentarios:this.FormRegistroServicios.value.comentarios,
            /*
            hay que eliminar prestador, esto lo sacamos dessde la session
            prestador:this.formPOSTRegistroServicio.value.prestador,
            */

          })
          .subscribe((respuesta: any) => {
            alert("Servicio Registrado")

          })
        }

        else{
          alert("Ingrese los datos correctamente")
          this.FormRegistroServicios.markAllAsTouched();

        }
  
}

  // METODO ELIMINAR SERVICIO
  eliminarServicio(id:string){
    this.estad_admin.DELETE_SERVICIO(id).subscribe(
      (data)=>{
      alert("Servicio Eliminado")
    
    },
      (error) =>{
        console.log("Servicio NO eliminado");
      })
  }



}
