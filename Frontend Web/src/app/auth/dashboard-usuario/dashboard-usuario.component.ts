import { HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';
import { LoginService } from 'src/app/servicios/login-service';

@Component({
  selector: 'app-dashboard-usuario',
  templateUrl: './dashboard-usuario.component.html',
  styleUrls: ['./dashboard-usuario.component.css']
})
export class DashboardUsuarioComponent implements OnInit {
// VARIABLES DE INFO USUARIO Y FICHA MEDICA
  getIdUser: any;
  getIdFichaMedica: any;
  getUser: any;

  
  message: string = ''; // Variable para almacenar el mensaje de bienvenida
// CONSTRUCTOR
  constructor(
    private paciente: EstadisUsuariosService,
    private formBuilder: FormBuilder,
    private router: Router,
    private serv_login: LoginService
  ) {} // CIERRA CONSTRUCTOR

// INICIA NG ON INIT
  ngOnInit(): void {
    this.message; // Llama al método para establecer el mensaje de bienvenida
    // TRAEMOS ID DEL LOCAL STORAGE
    this.getIdUser = this.serv_login.getUserId();

    // TRAEMOS INFO USER DEL LOCAL STORAGE
    this.getUser = this.serv_login.getUser();

    // GET ON INIT DE LA FICHA MEDICA
    this.getFichaMedica();

    // GET ON INIT TOGGLE DE LA FICHA MEDICA
    this.toggleFicheMedica();




  } // CIERRA NG ON INIT
  



//METODO PARA IR A LA FICHA MEDICA DESDE EL SIDEBAR
  public aFormFichaMedica(): void {
    this.router.navigateByUrl("/auth/registro3usuario");
  }


  public toggleFicheMedica(){
    const user = this.serv_login.getUser();
    if (this.getIdFichaMedica != undefined){
      //console.log("La ficha medica ya fue cargada")
      this.message = `Bienvenido ${user.username}!`
      return false
    }else{
      this.message = `Bienvenido ${user.username}!`
      //console.log("La ficha medica aun NO fue cargada")
      return true
    }
  }
  

// -----  METODO PARA TRAER LA FICHA MEDICA -----
// -----  METODO PARA TRAER LA FICHA MEDICA -----

  // TRAEMOS LA FICHA MEDICA (NO BORRAR ESTE METODO, ES EL QUE TRAE LA FICHA MEDICA)
  getFichaMedica(): void{
    this.paciente.getFichaMedicaIdUser(this.getIdUser).subscribe(
      (data:any)=>{
        // VERIFICAMOS QUE INFO TRAE
        //console.log("CARGA DE EXITOSA DE LA FICHA MEDICA:")
        //console.log(data);
        //console.log("Y EL ID DE LA FICHA MEDICA ES:") 
        //console.log(data.id);
        // COMO FUNCIONA BIEN GUARDAMOS EL ID DE LA FICHA MEDICA EN UNA VARIABLE
        this.getIdFichaMedica = data.id;
        // A ESTE ID LO PASAMOS COMO PARAMETRO DEL METODO agregarNotaGlucemia()
        // PERO NO POR ACA, SINO POR EL HTML
      },
      (error) => {
        console.log("ERROR EN LA CARGA DE LA FICHA MEDICA");
        console.log(error);
      })
    }

    // METODO PARA DESCARGAR PDF
    descargarPDF(){
      this.paciente.descargarDPF(this.getIdUser).subscribe(
        (data:Blob)=>{
          var file = new Blob([data], { type: 'application/pdf' })
          var fileURL = URL.createObjectURL(file);
          let downloadURL = window.URL.createObjectURL(data);
          let link = document.createElement('a');
          link.href=downloadURL;
          link.download="TusDatos_OneDrop"
          link.click

          // if you want to open PDF in new tab
          window.open(fileURL); 
          var a         = document.createElement('a');
          a.href        = fileURL; 
          a.target      = '_blank';
          a.download    = 'MisDatosOneDrop.pdf';
          document.body.appendChild(a);
          a.click();

          console.log("Se descargo exitosamente")
          console.log("El ID del User para el PDF es:")
          console.log(this.getIdUser)

        },
        (error)=>{

          console.log("Hubo un error en la descarga")
          console.log("El ID del User para el PDF es:")
          console.log(this.getIdUser)
        }
      )
    }

///////////////////////////////////////////////////


}
