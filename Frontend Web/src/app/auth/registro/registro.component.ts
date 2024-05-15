import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegistroService } from 'src/app/servicios/registro-service';

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

      username:["",[Validators.required,Validators.minLength(2),Validators.maxLength(30)]],
      password1:["",[Validators.required,Validators.minLength(9),Validators.maxLength(30)]], 
      password2:["",[Validators.required,Validators.minLength(9),Validators.maxLength(30)]], 
      email:["",[Validators.required,Validators.email]],
  
      nombre:["",[Validators.required,Validators.minLength(2),Validators.maxLength(30)]],
      apellido:["",[Validators.required,Validators.minLength(2),Validators.maxLength(30)]],
      nacimiento:["",[Validators.required]],
      sexo:["",[Validators.required]],
      dni:["",[Validators.required,Validators.minLength(8),Validators.maxLength(10)]],
      telefono:["",[Validators.required,Validators.minLength(9),Validators.maxLength(14)]],
    })

  }

  ///////////////////////////////////////////////////////////////////////           
    constructor(
      private formBuilder:FormBuilder,
      private router:Router,
      private serv_registro:RegistroService){};


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
            this.serv_registro.POST_REG_INICIAL({
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
            })
            .subscribe(
            // MANEJO DE SUSCRIPCIONES 
            (data) => {
                console.log("El token es: ");
                console.log(data);
              
              },
            (error) => {
                console.log(error);
              
              }
          ),
            // ACA HAY QUE ESPERAR UNA RESPUESTA 201 SI SE CREO USUARIO, SINO ERROR                
            // CODIGO QUE VALIDA, ES APARTE AL CONSUMO DEL SERVICIO
            //this.serv_registro.logout();
            this.router.navigateByUrl("/auth/login")
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


