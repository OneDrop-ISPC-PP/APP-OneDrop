import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EstadisAdminsService } from 'src/app/servicios/estadis-admins.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AuthService } from 'src/app/servicios/auth.service';


@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.css']
})
export class DashboardAdminComponent implements OnInit{

  estadisticas_admins:any
  comorbilidades_admins: any;
  servicios_admins:any;
  ingresos_admins: any;
  usuarios: any;

  formPOSTRegistroServicio: FormGroup | any;

  reg_servicios: any;
  dataid!: number;


  //////////////////////////////////////////////////////////////

  constructor(
    private estadistica:EstadisAdminsService,
    private formBuilder:FormBuilder,
    private admin:AuthService,
    private router: Router,
    private routerAct:ActivatedRoute
    
    ){}
  
///////////////////////////////////////////////////////////////
  ngOnInit():void{

    // OBJETO FORMBUILDER
      this.formPOSTRegistroServicio= this.formBuilder.group({

        nombre_servicio:["",Validators.required],
        descripcion_servicio:["",Validators.required],
        precio_servicio:["",Validators.required],
        comentarios_servicio:["",Validators.required],
      })

      
  //////////////////////////
  // METODOS SUSCRIPTOS AL LOS DATOS
    this.estadistica.muestraEstadisticas().subscribe({
      next:(estadisticas_S)=>{
        this.estadisticas_admins=estadisticas_S
      },
      error:(errorData)=>{
        console.error(errorData);
      } 
    })

  //////////////////////////
    this.estadistica.muestraComorbilidades().subscribe({
      next:(comorbilidades_S)=>{
        this.comorbilidades_admins=comorbilidades_S
      },
      error:(errorData)=>{
        console.error(errorData);
      } 
    })

  //////////////////////////
    this.estadistica.muestraUsuarios().subscribe({
      next:(usuarios_S)=>{
        this.usuarios=usuarios_S
      },
      error:(errorData)=>{
        console.error(errorData);
      } 
    })

  //////////////////////////
    this.estadistica.muestraServicios().subscribe({
      next:(servicio_S)=>{
        this.servicios_admins=servicio_S
      },
      error:(errorData)=>{
        console.error(errorData);
      } 
    })
  //////////////////////////
    this.estadistica.muestraIngresos().subscribe({
      next:(ingresos_S)=>{
        this.ingresos_admins=ingresos_S
      },
      error:(errorData)=>{
        console.error(errorData);
      } 
    })

  }
///////////////////////////////////////////////////////////////

    ///// METODOS GET PARTICULARES /////
    get nombre_servicio_GET(){
      return this.formPOSTRegistroServicio.controls['nombre_servicio'];
    }
    get descripcion_servicio_GET(){
      return this.formPOSTRegistroServicio.controls['descripcion_servicio'];
    }

    get precio_servicio_GET(){
      return this.formPOSTRegistroServicio.controls['precio_servicio'];
    }
    get comentarios_servicio_GET(){
      return this.formPOSTRegistroServicio.controls['comentarios_servicio'];
    }

  
    ///////////// METODOS DE SERVICIOS ///////////////
    // METODO GET
    getServicios(){
      this.estadistica.muestraServicios().subscribe({
        next:(servicio_S)=>{
          this.servicios_admins=servicio_S
        },
        error:(errorData)=>{
          console.error(errorData);
        } 
      })
    }

  // METODO DELETE
  eliminar(id:string){
    this.estadistica.DELETE(id).subscribe(()=>{
      alert("Servicio Eliminado")
      this.getServicios()
      console.log("el id de delete es"+id)
    })
  }



  // METODO POST
  enviarDatosDeServicio(){

    // SI EL FORMULARIO CUMPLE CON LA VALIDACION
    if(this.formPOSTRegistroServicio.valid){

      // Envia los datos al post
          this.estadistica.POSTRegistroServicio({
              /*
              EL ID ES AUTOGENERADO POR LA BD, AL REGISTRAR UN SERVICIO NO HACE FALTA MANDARLO, DEBEMOS ELIMINARLO DEL FORM
              id:this.formPOSTRegistroServicio.value.id,
              */
              nombre_servicio:this.formPOSTRegistroServicio.value.nombre_servicio,
              descripcion_servicio:this.formPOSTRegistroServicio.value.descripcion_servicio,
              precio_servicio:this.formPOSTRegistroServicio.value.precio_servicio,
              comentarios_servicio:this.formPOSTRegistroServicio.value.comentarios_servicio,
              /*
              hay que eliminar prestador, esto lo sacamos dessde la session
              prestador:this.formPOSTRegistroServicio.value.prestador,
              */

            })
            .subscribe((respuesta: any) => {
              alert("Servicio Registrado")
              this.getServicios()
            })
          }

          else{
            alert("Ingrese los datos correctamente")
        this.formPOSTRegistroServicio.markAllAsTouched();

          }
    
  }


  ///////// ///////////// /////////  
  respuesta:any=[];
  ///////// ///////////// /////////  









}
