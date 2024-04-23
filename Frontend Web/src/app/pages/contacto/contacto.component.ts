import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FormContactoService } from 'src/app/servicios/form-contacto.service';
import { loginInterface } from 'src/app/servicios/interfaces/loginInterface';
@Component({
  selector: 'app-contacto',
  templateUrl: './contacto.component.html',
  styleUrls: ['./contacto.component.css']
})
export class ContactoComponent implements OnInit {

  constructor(private formBuilder:FormBuilder,private router:Router , private serv_logincontact:FormContactoService){};
  profileForm=this.formBuilder.group({
    email:["",[Validators.required,Validators.email]],
    nombre:["",[Validators.required,Validators.minLength(7)]],
    mensaje:["",[Validators.required,Validators.minLength(7)]]
  });

  ngOnInit(): void {}


// /////////////////////////// METODO DE VERIFICACION DE FORMULARIO DE CONTACTO  ///////////////////////////
    verificacionContacto(){

      if(this.profileForm.valid){
  
        alert("Mensaje enviado")
        this.profileForm.reset(); // SI VALIDA CORRECTAMENTE SE REINICIAN LOS VALORES DE LOS CAMPOS
      } 
      else{
        // SI NO VALIDA TODOS LOS CAMPOS QUEDAN MARCADO EN ROJO
        this.profileForm.markAllAsTouched();
        alert("No se ingresaron correctamente los datos")

      }
    }


  ///// METODOS GET /////
  get email_GET(){
    return this.profileForm.controls['email'];
  }
  get nombre_GET(){
    return this.profileForm.controls['nombre'];
  }
  get mensaje_GET(){
    return this.profileForm.controls['mensaje'];
  }
}
