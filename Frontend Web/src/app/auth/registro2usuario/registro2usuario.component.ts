import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/servicios/auth.service';
import { loginInterface } from 'src/app/servicios/interfaces/loginInterface';



@Component({
  selector: 'app-registro2usuario',
  templateUrl: './registro2usuario.component.html',
  styleUrls: ['./registro2usuario.component.css']
})
export class Registro2usuarioComponent implements OnInit{

//////////////////////////////////////////////////////
  formPOSTRegistro2Usuarios: FormGroup | any;

//////////////////////////////////////////////////////
  ngOnInit(): void {
    
    this.formPOSTRegistro2Usuarios=this.formBuilder.group({
    
      // DATOS PERSONALES
      id_pacinte:[""],
      name:["",[Validators.required]],
      last_name:["",[Validators.required]],
      birthday:["",[Validators.required]],
      sex:["",[Validators.required]],
      phone:["",[Validators.required]],

    
    });
  }

/////////////////////////////////////////////////////
  constructor(
    private formBuilder:FormBuilder,
    private router:Router,
    private serv_Registro2Datos:AuthService){};


//////////////////// METODOS GET //////////////////////

    get name_GET(){
      return this.formPOSTRegistro2Usuarios.controls['name'];
    }
    get last_name_GET(){
      return this.formPOSTRegistro2Usuarios.controls['last_name'];
    }
    get birthday_GET(){
      return this.formPOSTRegistro2Usuarios.controls['birthday'];
    }
    get sex_GET(){
      return this.formPOSTRegistro2Usuarios.controls['sex'];
    }
    get phone_GET(){
      return this.formPOSTRegistro2Usuarios.controls['phone'];
    }
    
  

  /////////////////////////////////
  //////////// POST //////////////
  ////////////////////////////////

  // METODO DE VALIDACION Y ENVIO DE DATOS DEL SEGUNDO FORMULARIO DE REGISTRO
  enviarDatosRegistroDOS(){
    // si el formulario es valido
    if(this.formPOSTRegistro2Usuarios.valid){
        //Envia datos de creacion PACIENTE


        //this.serv_Registro2Datos.POST('http://localhost:8000/api/paciente/',
        this.serv_Registro2Datos.POST('http://localhost:8000/api/paciente/',
          {
          // INFORMACION QUE VAMOS A PASAR
            nombre_paciente:this.formPOSTRegistro2Usuarios.value.name,
            apellido_paciente:this.formPOSTRegistro2Usuarios.value.last_name,
            telefono_paciente:this.formPOSTRegistro2Usuarios.value.phone,
            fecha_nacimiento:this.formPOSTRegistro2Usuarios.value.birthday,
            sexo_paciente:this.formPOSTRegistro2Usuarios.value.sex,
          }
          )
          .subscribe((respuesta: any) => {
          }
        )

        // CODIGO QUE VALIDA, ES APARTE AL CONSUMO DEL SERVICIO
        this.router.navigateByUrl("/auth/registro3usuario")
        this.formPOSTRegistro2Usuarios.reset(); // SI VALIDA CORRECTAMENTE SE REINICIAN LOS VALORES DE LOS CAMPOS

        } 
    else{
        // SI NO VALIDA TODOS LOS CAMPOS QUEDAN MARCADO EN ROJO
        this.formPOSTRegistro2Usuarios.markAllAsTouched();
        alert("No se ingresaron correctamente los datos")

    }
  }

//////////////////////////////////////////////////////////////



      

























}
