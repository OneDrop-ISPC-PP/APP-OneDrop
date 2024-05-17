import { HttpBackend, HttpClient, HttpClientModule, HttpHeaderResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder,FormGroup,Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpHeaders } from '@angular/common/http';
import { LoginService } from 'src/app/servicios/login-service';
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
          username:["",[Validators.required,Validators.minLength(2),Validators.maxLength(30)]],
          password:["",[Validators.required,Validators.minLength(9),Validators.maxLength(30)]]
      
        });
    }
  //////////////////////////////////////////////////////////

    constructor(
      private formBuilder:FormBuilder,
      private router:Router,
      private serv_login:LoginService,
      private http : HttpClient){};



  ///// METODOS GET /////
  get username_GET(){
    return this.profileForm.controls.username;
  }
  get pass_GET(){
    return this.profileForm.controls.password;
  }

  ///// METODO DE SUBMIT DE LOGEO CON CONSUMO DE SERVICIO  /////
  submitLogin():void{
    // IF para comprobar si el formulario es valido
    
        if(this.profileForm.valid){
              this.serv_login.POST_LOGIN({
                    username:this.profileForm.value.username,
                    password:this.profileForm.value.password,
                  },)
                  .subscribe(
                    (data:any) => {
                      console.log("Los datos de logueo son"); console.log(data.token);
                      this.serv_login.loginUser(data.token);
                      console.log("Llego hasta el LOGIN USER (despues borrar)");
                      
                      /* CONSULTAMOS LOS DATOS DEL USUARIO QUE VIENEN EN EL TOKEN */
                      this.serv_login.getCurrentUser()
                        .subscribe((user:any)=>{

                          //ESTABLECEMOS AL USUARIO EN EL LOCAL STIRAGE
                          this.serv_login.setUser(user);

                          // IMPRIMIMOS LOS DATOS DEL USER EN LA CONSOLA
                          //console.log("Los datos de USER TOKEN son: ");
                          //console.log(user);

                          console.log("El role del USER es: ");
                          console.log(this.serv_login.getUserRole());
                          
                          // CONTROL DE DIRECCION DEPENDIENDO DEL TIPO DE USUARIO
                          if(this.serv_login.getUserRole() == "ADMIN"){

                            // SE REDIRIGE EL DASHBOARD ADMIN
                            this.router.navigateByUrl("auth/dash_admin");
                            this.serv_login.loginStatusSubject.next(true);

                          }else if(this.serv_login.getUserRole() == "USUARIO"){
                            // SE REDIRIGE EL DASHBOARD USER
                            this.router.navigateByUrl("auth/dash_user")
                            this.serv_login.loginStatusSubject.next(true);

                          }else{
                            // SE CIERRA LA SESION
                            //this.serv_login.logout();
                          }
                          
                      })
                    },
                    (error:any) => {console.log("Ocurrio un error");console.log(error);}
                );  
                  //this.router.navigateByUrl("/auth/dash_user")
                  //this.profileForm.reset(); // SI VALIDA CORRECTAMENTE SE REINICIAN LOS VALORES DE LOS CAMPOS
              } 
        else{

          // SI NO VALIDA TODOS LOS CAMPOS QUEDAN MARCADO EN ROJO
          this.profileForm.markAllAsTouched();
          alert("No se ingresaron correctamente los datos o no se reconoce el usuario")
        
        }
    }






}

