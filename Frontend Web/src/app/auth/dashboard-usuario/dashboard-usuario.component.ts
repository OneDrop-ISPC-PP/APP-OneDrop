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


// LISTAS
  listaNotasGlucemia: any = [];
  listaNotasTension: any = [];
  listaNotasPeso: any = [];


  notas_glucemia: any;
  
  // VARIABLES DE FORMULARIO
  formNotasGlucemia: FormGroup | any;
  formActualizarNota: FormGroup | any;
  formTensionArterial: FormGroup | any;
  formRegistroPeso: FormGroup | any;
  
  // VARIABLES VARIAS 
  servicios: any;
  serviciosEnCarrito: any[] = [];
  Snombre: string = '';
  Smonto: string = '';
  nuevoPedido: any[] = [];
  showForm: boolean = true;
  showFormTensionArterial: boolean = false;
  showFormRegistroPeso: boolean = false;
  showServicios: boolean = false;


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
    this.setUserWelcomeMessage(); // Llama al método para establecer el mensaje de bienvenida
    // TRAEMOS ID DEL LOCAL STORAGE
    this.getIdUser = this.serv_login.getUserId();

    // TRAEMOS INFO USER DEL LOCAL STORAGE
    this.getUser = this.serv_login.getUser();

    // GET ON INIT DE LA FICHA MEDICA
    this.getFichaMedica();

    // GETERS ON INIT DE LAS NOTAS
    this.getNotas();
    this.getNotasTension();
    this.getNotasPeso();
    
    this.toggleFicheMedica();

    this.getServicios();
    this.getCarrito();

    // ON INIT FORMULARIOS
    this.formNotasGlucemia = this.formBuilder.group({
      fecha: ['', Validators.required],
      valor: ['', [Validators.required]],
      comentario: ['', Validators.required]
    });

    this.formActualizarNota = this.formBuilder.group({
      fecha_registro: ['', Validators.required],
      valor_glucemia: ['', [Validators.required]],
      comentario_registro: ['', Validators.required]
    });

    this.formTensionArterial = this.formBuilder.group({
      fecha: ['', Validators.required],
      sistolica: ['', Validators.required],
      diastolica: ['', Validators.required],
      comentario: ['']
    });

    this.formRegistroPeso = this.formBuilder.group({
      fecha: ['', Validators.required],
      valor: ['', Validators.required],
      comentario: ['']
    });


  } // INICIA NG ON INIT
  
  setUserWelcomeMessage(): void {
    // Método para establecer el mensaje de bienvenida con el nombre de usuario
    const user = this.serv_login.getUser();
    if (user) {
      this.message = `Bienvenido ${user.username},`; // Suponiendo que el nombre de usuario está en el campo 'username'
    } else {
      this.message = 'Bienvenido'; // Mensaje de bienvenida predeterminado si no hay usuario logueado
    }
  }


//METODO PARA IR A LA FICHA MEDICA DESDE EL SIDEBAR
  public aFormFichaMedica(): void {
    this.router.navigateByUrl("/auth/registro3usuario");
  }

  // METODOS DEL SIDEBAR
  toggleForm(): void {
    this.showForm = !this.showForm;
    this.showFormRegistroPeso = false;
    this.showFormTensionArterial = false;
    this.showServicios = false;

  }

  toggleFormTensionArterial(): void {
    this.showFormTensionArterial = !this.showFormTensionArterial;
    this.showForm = false;
    this.showFormRegistroPeso = false;
    this.showServicios = false;

  }

  toggleFormRegistroPeso(): void {
    this.showFormRegistroPeso = !this.showFormRegistroPeso;
    this.showForm = false;
    this.showFormTensionArterial = false;
    this.showServicios = false;

  }

  public toggleFicheMedica(){
    if (this.getIdFichaMedica != undefined){
      //console.log("La ficha medica ya fue cargada")
      return false
    }else{
      //console.log("La ficha medica aun NO fue cargada")
      return true
    }
  }
  
  public toggleServicios(){
    this.showServicios = !this.showServicios;
    this.showForm = false;
    this.showFormRegistroPeso = false;
    this.showFormTensionArterial = false;

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
        this.router.navigateByUrl("/auth/dash_user");

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
      this.router.navigateByUrl("/auth/dash_user");

    
    },
      (error) =>{
        console.log("Nota NO eliminado");
        console.log("El ID es");
        console.log(id);
        console.log(error);
      })
  }

// -----  METODOS PARA TENSION ARTERIAL -----
// -----  METODOS PARA TENSION ARTERIAL -----
// GET NOTAS DE TENSION
getNotasTension(): void {
  this.paciente.GET_NOTAS_TENSION(this.getIdUser).subscribe(
    (data:any)=>{
      console.log("CARGA DE NOTAS DE TENSION EXITOSA, LOS DATOS SON:")
      console.log(data.registros);
      this.listaNotasTension = data.registros;
      
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
        this.getNotas();
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
      this.router.navigateByUrl("/auth/dash_user");

    
    },
      (error) =>{
        console.log("Nota NO eliminado");
        console.log("El ID es");
        console.log(id);
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
        this.getNotas();
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
      this.router.navigateByUrl("/auth/dash_user");
    },
      (error) =>{
        console.log("Nota NO eliminadA");
        console.log("El ID es");
        console.log(id);
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






agregarCarrito(servicio: any): void {
  this.paciente.agregaAlCarrito(servicio)
    .subscribe((data) => {
      console.log(data);
      this.getCarrito();
    });
}


// -----  DEMAS CODIGO -----
// -----  DEMAS CODIGO -----
  eliminar(id: string): void {
    this.paciente.DELETE(id).subscribe(() => {
      alert('Nota Eliminada');
      this.getNotas();
      console.log('el id de delete es' + id);
    });
  }

  cargarNotaParaActualizar(nota: any): void {
    this.formActualizarNota.patchValue({
      fecha_registro: nota.fecha_registro,
      valor_glucemia: nota.valor_glucemia,
      comentario_registro: nota.comentario_registro
    });
  }

  actualizarNota(id: string): void {
    if (this.formActualizarNota.valid) {
      const datosActualizados = {
        fecha_registro: this.formActualizarNota.value.fecha_registro,
        valor_glucemia: this.formActualizarNota.value.valor_glucemia,
        comentario_registro: this.formActualizarNota.value.comentario_registro
      };
      
      const idNumber: number = parseInt(id, 10);
      this.paciente.modificar2(datosActualizados, idNumber).subscribe((respuesta: any) => {
        alert('Nota actualizada');
      });
    } else {
      alert('Ingrese los datos correctamente');
      this.formActualizarNota.markAllAsTouched();
    }
  }





  eliminarServ(id:string): void {
    this.paciente.DELETE_SERV(id).subscribe(()=>{
      alert("Servicio eliminado del carrito");
      this.getCarrito();
      console.log("el id de delete es"+id);
    });
  }



  agregarNombre(value: string): void {
    this.Snombre = value;
  }

  agregarMonto(value2: string): void {
    this.Smonto = value2;
  }

  

  eliminarDelCarrito(index: number): void {
    if (index >= 0 && index < this.nuevoPedido.length) {
      this.nuevoPedido.splice(index, 1);
    }
  }

  calcularMontoTotal(): number {
    let total = 0;

    for (const item of this.nuevoPedido) {
      total += parseFloat(item[1]);
    }

    return total;
  }

  comprar(): void {
    this.router.navigateByUrl('/formulario_pago');
  }

  // CODIGO AGREGADO POR MARTIN

}
