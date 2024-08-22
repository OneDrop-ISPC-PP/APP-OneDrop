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
veracidadSiguiente:boolean = false;
// VARIABLES DE INFO USUARIO Y FICHA MEDICA
  getIdUser: any;
  getIdFichaMedica: any;
  getUser: any;

  infoMontos:number[] = []
  monto:number=0

  // VARIABLES DE SERVICIO
  servicios: any;

  // VARIABLES DE CARRITO
  serviciosEnCarrito: any[] = [];
  serviciosAdquiridos: any[] = this.serviciosEnCarrito;

  idCarrito:any;
  longCarrito:any;

  


  // VARIABLES DE INFO DE USER CARRITO
  getNombreUser:any
  getApellidoUser:any
  getDescripcion:any
  getMonto:any


  constructor(
    private paciente: EstadisUsuariosService,
    private formBuilder: FormBuilder,
    private router: Router,
    private serv_login: LoginService
  ) {} // CIERRA CONSTRUCTOR

  ngOnInit(): void {
  // INICIALIZAMOS LA VARIABLE DE CARRITO
    this.veracidadSiguiente = false;

    this.longCarrito = 0

    
    // TRAEMOS INFO USER DEL LOCAL STORAGE
    this.getUser = this.serv_login.getUser();
    this.getNombreUser= this.serv_login.getNameUser();
    this.getApellidoUser= this.serv_login.getSurnameUser();
    this.getIdUser = this.serv_login.getUserId();

    this.infoMontos


    // GET ON INIT DE LA FICHA MEDICA
    this.getFichaMedica();

    // INICIALIZAMOS PARA QUE SE CREE UN CARRITO
    this.getCarritoPorIdUser()
    this.nuevoCarrito24();


    // GET ON INIT DE LA FICHA MEDICA
    this.getServicios();
    this.idCarrito

    this.serviciosEnCarrito
  



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
        console.log("EL ID DE LA FICHA MEDICA ES:");
        this.getIdFichaMedica = data.id;
        console.log(this.getIdFichaMedica);

        // A ESTE ID LO PASAMOS COMO PARAMETRO DEL METODO agregarNotaGlucemia()
        // PERO NO POR ACA, SINO POR EL HTML
      },
      (error) => {
        console.log("ERROR EN LA CARGA DE LA FICHA MEDICA");
        console.log(error);
      })

    }








// -----  METODOS PARA SERVICIOS -----
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
// -----  METODOS DEL CARRITO -----


//METODO PARA CREAR UN NUEVO CARRITO
public nuevoCarrito24(){
  console.log("El id del paciente es:");
  console.log(this.getIdUser);

  //INFO PARA EL CARRITO
  let nombreCarrito:any = `El a nombre de ${this.getNombreUser}, ${this.getApellidoUser}`;
  let descCarrito:any = `Info de fecha y hora de creacion del carrito`;
  let precioCarrito:any = 0;
  let comenCarrito:any = `Comentarios del nuevo carrito`;

  // LLAMAMOS AL METODO POST
  this.paciente.nuevoCarrito24({
    nombre: nombreCarrito,
    descripcion: descCarrito,
    precio: precioCarrito,
    comentarios: comenCarrito
  },this.getIdUser).subscribe((data: any) => {
    console.log("Los datos del carrito son");
    console.log(data);

    console.log("El ID del carrito es:");
    console.log(data.id)
    
    console.log("Vemos si toma el cambio");
    this.idCarrito = data.id;
    console.log(this.idCarrito);

  }, (error: any) => {
    console.log("El nuevo carrito no fue registrado");
    console.log(error);

  });

}

// METODO GET PARA TRAER TODOS LOS CARRITOS, LUEGO LOS FILTRAMOS POR PACIENTE
getCarritoPorIdUser(){             // FALTA SOUCIONAR COMO GUARDAR EL ID DEL CARRITO
  this.paciente.getCarritoPorIdUser(this.getIdUser).subscribe(
    (data:any)=>{
      console.log("Los datos del carrito por ID son :");
      console.log(data);
      this.serviciosEnCarrito = data.servicios;

      this.longCarrito = this.serviciosEnCarrito.length
      console.log("La cantidad de servicios son:");
      console.log(this.longCarrito);
      this.idCarrito = data.id

      if(this.longCarrito != 0){
      this.veracidadSiguiente=true;
      }else{
        this.veracidadSiguiente=false;
      }

    },
    (error:any)=>{
      console.log("Error en la obtencion de los carritos");
      console.log(error);
    }
  )
}


// METODO PARA INSERTAR SERVICIOS EN EL CARRITO
servicioAlCarrito(id:any){
  // CREAMOS EL CARRITO (SE QUE ESTA MAL QUE INTENTE CREARLO DE NUEVO, TENGO QUE CAMBIARLO)
  this.paciente.getServicioPorId(id).subscribe(
    (data)=>{
      console.log("El servicio es");
      console.log(data);
      console.log("Ahora lo agregamos al carrito");
      // UNA VEZ QUE TENEMOS EL SERVICIO LO PONEMOS EN EL CARRITO
      this.paciente.postServicioEnCarrito(data,this.idCarrito,id).subscribe(
        (data)=>{ // QUEDAMOS ACA. QUEDAMOS EN SUBIR LOS SERVICIOS POR ID
          console.log("El servicio se puso en el carrito");
          console.log(data);
         this.getCarritoPorIdUser();

        },
        (error)=>{
          console.log("El servicio NO se puso en el carrito");
          console.log(error);
      alert("Este servicio ya se encuentra en el carrito")

        }
      )
    },
    (error)=>{
      console.log("Error al traer el servicio");
      console.log(error);
    }
  )
}

// METODO PARA ELIMINAR SERVICIO DEL CARRITO
eliminarServicioDelCarrito(id:string){
  this.paciente.delServicioEnCarrito(this.idCarrito,id).subscribe((data)=>{
    alert("Servicio Eliminado")
    this.getCarritoPorIdUser();

  },
    (error) =>{
      console.log("Servcio NO eliminado");
      console.log("El ID es");
      console.log(id);
      console.log(error);
    })
}


// METODO DEL BOTON DE SIGUIENTE DEL CARRITO
        // LA IDEA SERIA QUE PASE EL ID DEL CARRITO
siguienteCarrito(){
  this.router.navigateByUrl("/auth/dash_user/resumen_compra");
}

// METODO PARA IR DONDE SE MUESTRA MAS INFO DEL CARRITO
masInfo(){
  this.router.navigateByUrl("/auth/dash_user/mas_info_serv");
}









}
