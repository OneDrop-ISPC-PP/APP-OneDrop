import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';
import { LoginService } from 'src/app/servicios/login-service';
@Component({
  selector: 'app-info-tension-dash-user',
  templateUrl: './info-tension-dash-user.component.html',
  styleUrls: ['./info-tension-dash-user.component.css']
})
export class InfoTensionDashUserComponent implements OnInit{
// VARIABLES DE INFO USUARIO Y FICHA MEDICA
getIdUser: any;
getIdFichaMedica: any;
getUser: any;

// LISTAS
listaNotasTension: any = [];

// VARIABLES DE FORMULARIO
formTensionArterial: FormGroup | any;

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
    this.getNotasTension();

    // ON INIT FORMULARIOS
    this.formTensionArterial = this.formBuilder.group({
      fecha: ['', Validators.required],
      sistolica: ['', Validators.required],
      diastolica: ['', Validators.required],
      comentario: ['']
    });

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

// -----  METODOS PARA TENSION ARTERIAL -----
// -----  METODOS PARA TENSION ARTERIAL -----
// cambiar fecha de timestamp a Sáb 25, 02:50hs
timestampToDate(timestamp: string): string {
  // Convertir el timestamp a una fecha
  const date = new Date(timestamp);
  const dayOfWeek = ['Dom', 'Lun', 'Martes', 'Mié', 'Jue', 'Vie', 'Sáb'][date.getDay()];
  const dayOfMonth = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  return `${dayOfWeek} ${dayOfMonth}, ${hours}:${minutes}hs`;
}

// GET NOTAS DE TENSION
getNotasTension(): void {
  this.paciente.GET_NOTAS_TENSION(this.getIdUser).subscribe(
    (data:any)=>{
      console.log("CARGA DE NOTAS DE TENSION EXITOSA, LOS DATOS SON:")
      console.log(data.registros);
      // cambiar fecha de timestamp a Sáb 25, 02:50hs
      let listaModif = data.registros.map( (el: any) =>({
        ...el,
        fecha: this.timestampToDate(el.fecha)        
      }));       
      this.listaNotasTension = listaModif;
      
    },
    (error:any) => {
      console.log("ERROR EN LA CARGA DE LAS NOTAS DE TENSION");
      console.log(error);
    });
}
// METODO PARA AGREGAR NOTA DE TENSION ARTERIAL
  agregarNotaTensionArterial(id: any): void {
    if (this.formTensionArterial.valid) {
      this.paciente.agregarTensionArterial({
        fecha: this.formTensionArterial.value.fecha,
        sistolica: this.formTensionArterial.value.sistolica,
        diastolica: this.formTensionArterial.value.diastolica,
        comentario: this.formTensionArterial.value.comentario
      },id).subscribe((data: any) => {
        console.log("Datos registrados de tension");
        console.log(data);
        console.log("El id pasado de la ficha medica es");
        console.log(id);
        this.getNotasTension();
        this.formTensionArterial.reset();
      }, (error: any) => {
        console.log("Datos de tension no fueron registrados ");
        console.log(error);
        console.log(id);

      });
    } else {
      alert('Ingrese los datos correctamente');
      console.log(id);

      this.formTensionArterial.markAllAsTouched();
    }
  }

  // METODO PARA ELIMINAR NOTA DE TENSION
  eliminarNotaTension(id:string){
    this.paciente.DELETE_NOTA_TENSION(id).subscribe((data)=>{
      alert("Nota Tension Eliminada")
      this.getNotasTension();

    
    },
      (error) =>{
        console.log("Nota NO eliminado");
        console.log("El ID es");
        console.log(id);
        console.log(error);
      })
  }




}
