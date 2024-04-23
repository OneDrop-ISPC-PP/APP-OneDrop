import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/servicios/auth.service';

// SE USARAN FORMULARIOS REACTIVOS Y VALIDACIONES SINCRONICAS



@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})

export class RegistroComponent implements OnInit{

////////////////////////////////////////////////////////////////////////
  formPOSTRegistroUsuario: FormGroup | any;

  
//////////////////////////////////////////////////////////////////////
  ngOnInit(): void {
    
    this.formPOSTRegistroUsuario= this.formBuilder.group({

      username:["",[Validators.required]],
      password1:["",[Validators.required,Validators.minLength(9)]],
      password2:["",[Validators.required,Validators.minLength(9)]],
      email:["",[Validators.required,Validators.email]],
  
      nombre:["",[Validators.required]],
      apellido:["",[Validators.required]],
      nacimiento:["",[Validators.required]],
      sexo:["",[Validators.required]],
      dni:["",[Validators.required]],
      telefono:["",[Validators.required]]
    })

  }

  ///////////////////////////////////////////////////////////////////////           
    constructor(
      private formBuilder:FormBuilder,
      private router:Router,
      private serv_registro:AuthService){};


  //////////// METODOS GET ///////////
  get email_GET(){
    return this.formPOSTRegistroUsuario.controls['email'];
  }
  get password_GET(){
    return this.formPOSTRegistroUsuario.controls['password1'];
  }
  get password2_GET(){
    return this.formPOSTRegistroUsuario.controls['password2'];
  }
  get username_GET(){
    return this.formPOSTRegistroUsuario.controls['username'];
  }
  get name_GET(){
    return this.formPOSTRegistroUsuario.controls['nombre'];
  }
  get last_name_GET(){
    return this.formPOSTRegistroUsuario.controls['apellido'];
  }
  get birthday_GET(){
    return this.formPOSTRegistroUsuario.controls['nacimiento'];
  }
  get sex_GET(){
    return this.formPOSTRegistroUsuario.controls['sexo'];
  }
  get phone_GET(){
    return this.formPOSTRegistroUsuario.controls['telefono'];
  }
  get dni_GET(){
    return this.formPOSTRegistroUsuario.controls['dni'];
  }
  



  //////////// METODO POST //////////////
  enviarDatosRegistroInicial(){
    // SI EL FORMULARIO CUMPLE CON LA VALIDACION
      if(this.formPOSTRegistroUsuario.valid){

            // Envia los datos
            //this.serv_registro.POST( 'http://localhost:3000/REGISTRO_INICIAL',
            //this.serv_registro.POST( 'http://127.0.0.1:8080/auth/signup/',
            console.log("Datos ingresados 01");
            this.serv_registro.POST('http://localhost:8080/auth/register',
            {
              // INFORMACION QUE VAMOS A PASAR  
              username:this.formPOSTRegistroUsuario.value.username,
              password1:this.formPOSTRegistroUsuario.value.password1,
              password2:this.formPOSTRegistroUsuario.value.password2,
              apellido:this.formPOSTRegistroUsuario.value.apellido,
              nombre:this.formPOSTRegistroUsuario.value.nombre,
              telefono:this.formPOSTRegistroUsuario.value.telefono,
              dni:this.formPOSTRegistroUsuario.value.dni,
              email:this.formPOSTRegistroUsuario.value.email,
              nacimiento:this.formPOSTRegistroUsuario.value.nacimiento,
              sexo:this.formPOSTRegistroUsuario.value.sexo,
            }
            
            )
            .subscribe((respuesta: any) => {
 
            })
            console.log("Datos ingresados");
           
            // ACA HAY QUE ESPERAR UNA RESPUESTA 201 SI SE CREO USUARIO, SINO ERROR
                
            // CODIGO QUE VALIDA, ES APARTE AL CONSUMO DEL SERVICIO
            this.router.navigateByUrl("/auth/registro3usuario")
            this.formPOSTRegistroUsuario.reset(); // SI VALIDA CORRECTAMENTE SE REINICIAN LOS VALORES DE LOS CAMPOS

      } 
      else{
            // SI NO VALIDA TODOS LOS CAMPOS QUEDAN MARCADO EN ROJO
            this.formPOSTRegistroUsuario.markAllAsTouched();
            alert("No se ingresaron correctamente los datos")
          
      }
  }

/////////////////////////////////////////////////////////////

}


