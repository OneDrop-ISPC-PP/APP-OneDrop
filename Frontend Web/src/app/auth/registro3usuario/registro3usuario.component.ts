import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegistroFichaMedicaService } from 'src/app/servicios/registro-ficha-medica.service';
import { LoginComponent } from '../login/login.component';
import { LoginService } from 'src/app/servicios/login-service';


@Component({
  selector: 'app-registro3usuario',
  templateUrl: './registro3usuario.component.html',
  styleUrls: ['./registro3usuario.component.css']
})
export class Registro3usuarioComponent {

  /////////////////////////////////////////////////////
  constructor(
    private formBuilder:FormBuilder,
    private router:Router,
    private serv_RegistroFichaMedica:RegistroFichaMedicaService,
    private serv_login: LoginService
  ){};

//////////////////////////////////////////////////////
  formPOSTRegistro3Usuarios: FormGroup | any;

  getId = this.serv_login.getUserId();
  getUser = this.serv_login.getUser();



//////////////////////////////////////////////////////
  ngOnInit(): void {
    
    this.formPOSTRegistro3Usuarios=this.formBuilder.group({
      
      // DATOS MEDICOS
      //id_paciente:["",[Validators.required]],
      tipo_diabetes:["",[Validators.required]],
      terapia_insulina:["",[Validators.required]],
      terapia_pastillas:["",[Validators.required]],
      tipo_glucometro:["",[Validators.required]],
      tipo_sensor:["",[Validators.required]],
      objetivo_glucosa:["",[Validators.required]],
      comorbilidades:["",[Validators.required]],
      peso:["",[Validators.required]],


    
    });


  }



//////////////////// METODOS GET //////////////////////

    /* get id_paciente_GET(){
      return this.formPOSTRegistro3Usuarios.controls['id_paciente'];
    }
    */
    get tipo_diabetes_GET(){
      return this.formPOSTRegistro3Usuarios.controls['tipo_diabetes'];
    }
    get terapia_insulina_GET(){
      return this.formPOSTRegistro3Usuarios.controls['terapia_insulina'];
    }
    get terapia_pastillas_GET(){
    return this.formPOSTRegistro3Usuarios.controls['terapia_pastillas'];
    }
    get tipo_glucometro_GET(){
    return this.formPOSTRegistro3Usuarios.controls['tipo_glucometro'];
    }
    get tipo_sensor_GET(){
    return this.formPOSTRegistro3Usuarios.controls['tipo_sensor'];
    }
    get comorbilidades_GET(){
    return this.formPOSTRegistro3Usuarios.controls['comorbilidades'];
    }
    get objetivo_glucosa_GET(){
      return this.formPOSTRegistro3Usuarios.controls['objetivo_glucosa'];
    }
    get peso_GET(){
      return this.formPOSTRegistro3Usuarios.controls['peso'];
    }


  /////////////////////////////////
  //////////// POST //////////////
  ////////////////////////////////
  enviarDatosFichaMedica(){

        if(this.formPOSTRegistro3Usuarios.valid){
     //Envia datos de creacion FICHA MEDICA
//     this.serv_Registro3Datos.POST('http://localhost:8000/api/paciente/ficha_medica/',{
        this.serv_RegistroFichaMedica.POST_REG_FICHA_MEDICA('http://localhost:8080/fichaMedica/',
        {
            // esta funcion deberia llamarse post a secas, porque es polimorfica a cualquier post, osea no ahcce nada especial referido a que sea un post de registros de ususarios...
            //Aid:this.getId,
            id_paciente:this.getId,
            tipo_diabetes:this.formPOSTRegistro3Usuarios.value.tipo_diabetes,
            terapia_insulina:this.formPOSTRegistro3Usuarios.value.terapia_insulina,
            terapia_pastillas:this.formPOSTRegistro3Usuarios.value.terapia_pastillas,
            tipo_glucometro:this.formPOSTRegistro3Usuarios.value.tipo_glucometro,
            tipo_sensor:this.formPOSTRegistro3Usuarios.value.tipo_sensor,
            objetivo_glucosa:this.formPOSTRegistro3Usuarios.value.objetivo_glucosa,
            comorbilidades:this.formPOSTRegistro3Usuarios.value.comorbilidades,
          })
          .subscribe(
          // MANEJO DE SUSCRIPCIONES 
            (data) => {
              console.log("SE ENVIARON LOS DATOS DE LA FICHA MEDICA")
              console.log(data);
              //console.log(this.getId)
            },
            (error) => {
              console.log(error);
              console.log("NO enviaron los datos del formulario")

              //console.log(this.getId)             
            },
          )
         // CODIGO QUE VALIDA, ES APARTE AL CONSUMO DEL SERVICIO
        this.router.navigateByUrl("/auth/dash_user")
        this.formPOSTRegistro3Usuarios.reset(); // SI VALIDA CORRECTAMENTE SE REINICIAN LOS VALORES DE LOS CAMPOS
        }
        else{
            // SI NO VALIDA TODOS LOS CAMPOS QUEDAN MARCADO EN ROJO
            this.formPOSTRegistro3Usuarios.markAllAsTouched();
            alert("No se ingresaron correctamente los datos")
            console.log(this.getId)             


    }


  }

}
