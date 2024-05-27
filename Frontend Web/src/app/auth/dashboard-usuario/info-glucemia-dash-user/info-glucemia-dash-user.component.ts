import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';
import { LoginService } from 'src/app/servicios/login-service';

@Component({
  selector: 'app-info-glucemia-dash-user',
  templateUrl: './info-glucemia-dash-user.component.html',
  styleUrls: ['./info-glucemia-dash-user.component.css']
})
export class InfoGlucemiaDashUserComponent implements OnInit{
// VARIABLES DE INFO USUARIO Y FICHA MEDICA
getIdUser: any;
getIdFichaMedica: any;
getUser: any;

// LISTAS
listaNotasGlucemia: any = [];

// VARIABLES DE FORMULARIO
formNotasGlucemia: FormGroup | any;


  // CONSTRUCTOR
  constructor(
    private paciente: EstadisUsuariosService,
    private formBuilder: FormBuilder,
    private router: Router,
    private serv_login: LoginService
  ) {} // CIERRA CONSTRUCTOR


// INICIA NG ON INIT
ngOnInit(): void {
    // TRAEMOS INFO USER DEL LOCAL STORAGE
    this.getUser = this.serv_login.getUser();

    // TRAEMOS ID DEL LOCAL STORAGE
    this.getIdUser = this.serv_login.getUserId();  

    // TRAEMOS INFO USER DEL LOCAL STORAGE
    this.getUser = this.serv_login.getUser();  

    // GET ON INIT DE LA FICHA MEDICA
    this.getFichaMedica();

    // GETERS ON INIT DE LAS NOTAS
    this.getNotas();

    // ON INIT FORMULARIOS
    this.formNotasGlucemia = this.formBuilder.group({
      fecha: ['', Validators.required],
      valor: ['', [Validators.required]],
      comentario: ['', Validators.required]
    });

  } // CIERRA ON INIT

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




// -----  METODOS PARA GLUCEMIA -----
// -----  METODOS PARA GLUCEMIA -----

  // GET NOTAS DE GLUCEMIA
  getNotas(): void {
    this.paciente.GET_NOTAS_GLUCEMIA(this.getIdUser).subscribe(
      (data:any)=>{
        console.log("CARGA DE NOTAS DE GLUCEMIA EXITOSA, LOS DATOS SON:")
        console.log(data.registros);
        this.listaNotasGlucemia = data.registros;
        
      },
      (error:any) => {
        console.log("ERROR EN LA CARGA DE LAS NOTAS DE GLUCEMIA");
        console.log(error);
      });
  }

  // METODO PARA AGREGAR NOTA DE GLUCEMIA
  agregarNotaGlucemia(id: any): void {
    if (this.formNotasGlucemia.valid) {
      this.paciente.nuevaNotaGlucemia({
        fecha: this.formNotasGlucemia.value.fecha,
        valor: this.formNotasGlucemia.value.valor,
        comentario: this.formNotasGlucemia.value.comentario
      }, id).subscribe((data: any) => {
        console.log("Datos registrados de glucemia");
        console.log(data);
        console.log("El id pasado de la ficha medica es");
        console.log(id);
        this.getNotas();
        this.formNotasGlucemia.reset();
        this.router.navigateByUrl("/auth/dash_user/info_glucemia");

      }, (error: any) => {
        console.log("Datos de glucemia no fueron registrados ");
        console.log(error);
      });
    } else {
      alert('Ingrese los datos correctamente');
      this.formNotasGlucemia.markAllAsTouched();
    }
  }
  // METODO PARA ELIMINAR NOTA DE GLUCEMIA
  eliminarNotaGlucemia(id:string){
    this.paciente.DELETE_NOTA_GLUCEMIA(id).subscribe((data)=>{
      alert("Nota Eliminada")
      this.getNotas();

    
    },
      (error) =>{
        console.log("Nota NO eliminado");
        console.log("El ID es");
        console.log(id);
        console.log(error);
      })
  }

}

