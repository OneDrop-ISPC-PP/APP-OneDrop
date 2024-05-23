import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';
import { LoginService } from 'src/app/servicios/login-service';

@Component({
  selector: 'app-info-peso-dash-user',
  templateUrl: './info-peso-dash-user.component.html',
  styleUrls: ['./info-peso-dash-user.component.css']
})
export class InfoPesoDashUserComponent implements OnInit{
// VARIABLES DE INFO USUARIO Y FICHA MEDICA
getIdUser: any;
getIdFichaMedica: any;
getUser: any;

// LISTAS
listaNotasPeso: any = [];

// VARIABLES DE FORMULARIO
formRegistroPeso: FormGroup | any;

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

  // GET ON INIT DE LAS NOTAS DE PESO
  this.getNotasPeso();

  // GET ON INIT DE LAS NOTAS DE PESO
  this.formRegistroPeso = this.formBuilder.group({
    fecha: ['', Validators.required],
    valor: ['', Validators.required],
    comentario: ['']
  });


} // INICIA NG ON INIT

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

// -----  METODOS PARA PESO -----
// -----  METODOS PARA PESO -----

// GET NOTAS DE TENSION
getNotasPeso(): void {
  this.paciente.GET_NOTAS_PESO(this.getIdUser).subscribe(
    (data:any)=>{
      console.log("CARGA DE NOTAS DE PESO, LOS DATOS SON:")
      console.log(data.registros);
      this.listaNotasPeso = data.registros;
      
    },
    (error:any) => {
      console.log("ERROR EN LA CARGA DE LAS NOTAS DE PESO");
      console.log(error);
    });
}

  agregarNotaRegistroPeso(id:any): void {
    if (this.formRegistroPeso.valid) {
      this.paciente.nuevaNotaPeso({
        fecha: this.formRegistroPeso.value.fecha,
        valor: this.formRegistroPeso.value.valor,
        comentario: this.formRegistroPeso.value.comentario
      },id).subscribe((data: any) => {
        console.log("Datos registrados de peso");
        console.log(data);
        console.log("El id pasado de la ficha medica es");
        console.log(id);
        this.getNotasPeso();
        this.formRegistroPeso.reset();
      }, (error: any) => {
        console.log("Datos de tension no fueron registrados ");
        console.log(error);
        console.log(id);

      });
    }
  }
  // METODO PARA ELIMINAR NOTA DE PESO
  eliminarNotaPeso(id:string){
    this.paciente.DELETE_NOTA_PESO(id).subscribe((data)=>{
      alert("Nota Peso Eliminada")
      this.getNotasPeso();
    },
      (error) =>{
        console.log("Nota NO eliminadA");
        console.log("El ID es");
        console.log(id);
        console.log(error);
      })
  }




}
