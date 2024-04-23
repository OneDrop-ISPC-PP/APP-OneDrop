import { HttpBackend, HttpClient, HttpClientModule, HttpHeaderResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from 'src/app/servicios/auth.service';
import { Emitters } from 'src/app/emitters/emitters';



// SE USARAN FORMULARIOS REACTIVOS Y VALIDACIONES SINCRONICAS

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit{
  //////////////////////////////////////////////////////////
    profileForm:FormGroup | any;



  //////////////////////////////////////////////////////////
    ngOnInit(): void {

        this.profileForm=this.formBuilder.group({

          //email:["",[Validators.required,Validators.email]],
          username:["",[Validators.required]],
          password:["",[Validators.required,Validators.minLength(9)]]
      
        });
    }
  //////////////////////////////////////////////////////////

    constructor(
      private formBuilder:FormBuilder,
      private router:Router,
      private serv_login:AuthService,
      private http : HttpClient){};




  ///// METODO DE SUBMIT DE LOGEO FUNCIONAL SIN CONSUMO DE SERVICIO /////
    submit():void{
      // IF para comprobar si el formulario es valido
          if(this.profileForm.valid){

                  this.http.post('http://localhost:8000/api/auth/login/', this.profileForm.getRawValue(), {withCredentials: true})

                  .subscribe((data) =>{
                  this.router.navigateByUrl("/")
                  console.log("Los datos de logueo son:" + data);
                });                
                
            this.router.navigateByUrl("/auth/registro2usuario")                
                this.profileForm.reset(); // SI VALIDA CORRECTAMENTE SE REINICIAN LOS VALORES DE LOS CAMPOS
          } 
          else{

            // SI NO VALIDA TODOS LOS CAMPOS QUEDAN MARCADO EN ROJO
            this.profileForm.markAllAsTouched();
            alert("No se ingresaron correctamente los datos o no se reconoce el usuario")
          
          }
      }


  ///// METODO DE SUBMIT DE LOGEO CON CONSUMO DE SERVICIO  /////

  submit2():void{
    // IF para comprobar si el formulario es valido
    
        if(this.profileForm.valid){
              this.serv_login.POST('http://localhost:8000/auth/login/',{
                    password:this.profileForm.value.password,
                    email:this.profileForm.value.username,
                  },)
                  .subscribe((data) =>{
                    console.log("Los datos de logueo son:" + data);
                  });  

                  this.router.navigateByUrl("/auth/dash_user")
                  this.profileForm.reset(); // SI VALIDA CORRECTAMENTE SE REINICIAN LOS VALORES DE LOS CAMPOS
              } 
        else{

          // SI NO VALIDA TODOS LOS CAMPOS QUEDAN MARCADO EN ROJO
          this.profileForm.markAllAsTouched();
          alert("No se ingresaron correctamente los datos o no se reconoce el usuario")
        
        }
    }


  ///// METODOS GET /////
    get username_GET(){
      return this.profileForm.controls.username;
    }
    get pass_GET(){
      return this.profileForm.controls.password;
    }





}

