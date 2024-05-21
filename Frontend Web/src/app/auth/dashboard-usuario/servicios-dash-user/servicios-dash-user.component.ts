import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';
import { LoginService } from 'src/app/servicios/login-service';

@Component({
  selector: 'app-servicios-dash-user',
  templateUrl: './servicios-dash-user.component.html',
  styleUrls: ['./servicios-dash-user.component.css']
})
export class ServiciosDashUserComponent implements OnInit{
// VARIABLES CARRITO
veracidadCarrito:boolean = false;
// VARIABLES DE INFO USUARIO Y FICHA MEDICA
  getIdUser: any;
  getIdFichaMedica: any;
  getUser: any;

  // VARIABLES DE SERVICIO
  servicios: any;

  // VARIABLES DE CARRITO
  serviciosEnCarrito: any[] = [];


  constructor(
    private paciente: EstadisUsuariosService,
    private formBuilder: FormBuilder,
    private router: Router,
    private serv_login: LoginService
  ) {} // CIERRA CONSTRUCTOR

  ngOnInit(): void {
  // INICIALIZAMOS LA VARIABLE DE CARRITO
    this.veracidadCarrito = false;

    this.getIdUser = this.serv_login.getUserId();

    // TRAEMOS INFO USER DEL LOCAL STORAGE
    this.getUser = this.serv_login.getUser();

    // GET ON INIT DE LA FICHA MEDICA
    this.getFichaMedica();

    // GET ON INIT DE LA FICHA MEDICA
    this.getServicios();
    this.getCarrito();

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


    // -----  METODOS PARA SERVICIOS -----
getServicios(): void {
  this.paciente.GET_SERVICIOS(this.getIdUser).subscribe(
    (data:any)=>{
      console.log("LOS SERVICIOS SON:")
      console.log(data.registros);
      this.servicios = data.registros;
      
    },
    (error:any) => {
      console.log("ERROR EN LA CARGA DE LOS SERVICIOS");
      console.log(error);
    });
}
// -----  METODOS DEL CARRITO -----
public nuevoCarrito24(){
  this.veracidadCarrito = true;
  return this.paciente.nuevoCarrito24
}

public nuevoCarrito24false(){
  this.veracidadCarrito = false;
}

getCarrito(): void {
  this.paciente.muestraCarritoAUsuario().subscribe(
    (servicios_C: any) => {
      this.serviciosEnCarrito = servicios_C as any[];
    },
    (errorData: any) => {
      console.error(errorData);
    }
  );
}

agregarAlCarrito(precio:any, nombre:any): void {
  const nuevoItem = [precio, nombre];
  this.serviciosEnCarrito.push(nuevoItem);
  console.log("Servicio carrito");
  console.log(nuevoItem);
  console.log(this.serviciosEnCarrito);


}



}
