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
  notas_glucemia: any;
  servicios: any;

// VARIABLES DE FORMULARIO
  formNotasGlucemia: FormGroup | any;
  formActualizarNota: FormGroup | any;
  formTensionArterial: FormGroup | any;
  formRegistroPeso: FormGroup | any;

// VARIABLES VARIAS 
  serviciosCarrito: any[] = [];
  Snombre: string = '';
  Smonto: string = '';
  nuevoPedido: any[] = [];
  showForm: boolean = false;
  showFormTensionArterial: boolean = false;
  showFormRegistroPeso: boolean = false;

// CONSTRUCTOR
  constructor(
    private paciente: EstadisUsuariosService,
    private formBuilder: FormBuilder,
    private router: Router,
    private serv_login: LoginService
  ) {} // CIERRA CONSTRUCTOR

// INICIA NG ON INIT
  ngOnInit(): void {
    // TRAEMOS ID DEL LOCAL STORAGE
    this.getIdUser = this.serv_login.getUserId();

    // TRAEMOS INFO USER DEL LOCAL STORAGE
    this.getUser = this.serv_login.getUser();

    // GET ON INIT DE LA FICHA MEDICA
    this.getFichaMedica();

    // GETERS ON INIT DE LAS NOTAS
    this.getNotas();
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
      fecha_registro_tension: ['', Validators.required],
      sistolica: ['', Validators.required],
      diastolica: ['', Validators.required],
      comentario_tension: ['']
    });

    this.formRegistroPeso = this.formBuilder.group({
      fecha_registro_peso: ['', Validators.required],
      valor_peso: ['', Validators.required],
      comentario_peso: ['']
    });


  } // INICIA NG ON INIT


//METODO PARA IR A LA FICHA MEDICA DESDE EL SIDEBAR
  public aFormFichaMedica(): void {
    this.router.navigateByUrl("/auth/registro3usuario");
  }

  // METODOS DEL SIDEBAR
  toggleForm(): void {
    this.showForm = !this.showForm;
  }

  toggleFormTensionArterial(): void {
    this.showFormTensionArterial = !this.showFormTensionArterial;
  }

  toggleFormRegistroPeso(): void {
    this.showFormRegistroPeso = !this.showFormRegistroPeso;
  }
  


// -----  METODOS PARA AGREGAR TODAS NOTAS -----
// -----  METODOS PARA AGREGAR TODAS NOTAS -----

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
      }, (error: any) => {
        console.log("Datos de glucemia no fueron registrados ");
        console.log(error);
      });
    } else {
      alert('Ingrese los datos correctamente');
      this.formNotasGlucemia.markAllAsTouched();
    }
  }

  // METODO PARA AGREGAR NOTA DE TENSION ARTERIAL
  agregarNotaTensionArterial(): void {
    if (this.formTensionArterial.valid) {
      this.paciente.agregarTensionArterial({
        fecha_registro: this.formTensionArterial.value.fecha_registro_tension,
        sistolica: this.formTensionArterial.value.sistolica,
        diastolica: this.formTensionArterial.value.diastolica,
        comentario: this.formTensionArterial.value.comentario_tension
      }).subscribe((respuesta: any) => {
        alert('Registro de tensión arterial agregado');
        this.formTensionArterial.reset();
      });
    } else {
      alert('Ingrese los datos correctamente');
      this.formTensionArterial.markAllAsTouched();
    }
  }

  // METODO PARA AGREGAR NOTA DE TENSION ARTERIAL
  agregarNotaRegistroPeso(): void {
    if (this.formRegistroPeso.valid) {
      this.paciente.agregarRegistroPeso({
        fecha_registro: this.formRegistroPeso.value.fecha_registro_peso,
        valor_peso: this.formRegistroPeso.value.valor_peso,
        comentario: this.formRegistroPeso.value.comentario_peso
      }).subscribe((respuesta: any) => {
        alert('Registro de peso agregado');
        this.formRegistroPeso.reset();
      });
    } else {
      alert('Ingrese los datos correctamente');
      this.formRegistroPeso.markAllAsTouched();
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

// -----  METODOS PARA TRAER TODAS NOTAS -----
// -----  METODOS PARA TRAER TODAS NOTAS -----

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

  getCarrito(): void {
    this.paciente.muestraCarritoAUsuario().subscribe(
      (servicios_C: any) => {
        this.serviciosCarrito = servicios_C as any[];
      },
      (errorData: any) => {
        console.error(errorData);
      }
    );
  }

  agregarCarrito(servicio: any): void {
    this.paciente.agregaAlCarrito(servicio)
      .subscribe((data) => {
        console.log(data);
        this.getCarrito();
      });
  }

  eliminarServ(id:string): void {
    this.paciente.DELETE_SERV(id).subscribe(()=>{
      alert("Servicio eliminado del carrito");
      this.getCarrito();
      console.log("el id de delete es"+id);
    });
  }

  getServicios(): void {
    this.paciente.muestraServicioAUsuario().subscribe({
      next: (servicios_S) => {
        this.servicios = servicios_S;
      },
      error: (errorData) => {
        console.error(errorData);
      }
    });
  }

  agregarNombre(value: string): void {
    this.Snombre = value;
  }

  agregarMonto(value2: string): void {
    this.Smonto = value2;
  }

  agregarAlCarrito(): void {
    const nuevoItem = [this.Snombre, this.Smonto];

    if (!this.nuevoPedido) {
      this.nuevoPedido = [];
    }

    this.nuevoPedido.push(nuevoItem);

    this.Snombre = '';
    this.Smonto = '';
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
