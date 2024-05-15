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
  constructor(
    private paciente: EstadisUsuariosService,
    private formBuilder: FormBuilder,
    private router: Router,
    private serv_login: LoginService
  ) {
   
  }

// NO PUEDO TRAER LA FICHA MEDICA Y POR LO TANTO TMPOCO SU ID PARA HACER LOS REGISTROS
  getId = this.serv_login.getUserId();
  idFichaMedica = 5 // HARDCODEADA


  getUser = this.serv_login.getUser();
  //getFichaMedica = this.paciente.getFichaMedica(this.getId);

  // HAY REGISTRO DE GLUCEMIA, VER COMO TRAERLOS. QUE SERIA LO MISMO QUE TRAER LA FICHA MEDICA.
  // CAPAZ ESTAMOS TRAYENDO MAL Y POR ESO NO SE VE NADA :l


  listaNotasGlucemia:any = [];

  notas_glucemia: any;
  servicios: any;

  formNotasGlucemia: FormGroup|any;
  formActualizarNota: FormGroup|any;
  formTensionArterial: FormGroup|any;
  formRegistroPeso: FormGroup|any; // Formulario de registro de peso

  serviciosCarrito: any[] = [];
  Snombre: string = '';
  Smonto: string = '';
  nuevoPedido: any[] = [];
  showForm: boolean = false;
  showFormTensionArterial: boolean = false;
  showFormRegistroPeso: boolean = false; // Propiedad para controlar el formulario de registro de peso



  // EMPIEZA ON INIT
  ngOnInit(): void {
    // TREAMOS LAS NOTAS DE GLUCEMIA
    this.paciente.GET_NOTAS_GLUCEMIA(this.getId).subscribe(
      (data:any)=>{
        this.listaNotasGlucemia = data;
        console.log("CARGA DE NOTAS DE GLUCEMIA EXITOSA, LOS DATOS SON:")
        console.log(data);

      },
      (error:any) => {
        console.log("ERROR EN LA CARGA DE LAS NOTAS DE GLUCEMIA");
        console.log(error);

      })


    // TREAMOS LA FICHA MEDICA
    this.paciente.getFichaMedica(this.getId).subscribe(
      (data:any)=>{
        this.listaNotasGlucemia = data;
        console.log("CARGA DE LA FICHA MEDICA EXITOSA, LOS DATOS SON:")
        console.log(data);
        console.log("El ID de la ficha medica es:")
        // GUARDAMOS EL ID EN LA VARIABLE idFichaMedica

        console.log(data.id);

      },
      (error:any) => {
        console.log("ERROR EN LA CARGA DE LA FICHA MEDICA");
        console.log(error);
      })

    
    // BUILDER DE NOTAS DE GLUCEMIA
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

    // Inicializamos el formulario de registro de peso
    this.formRegistroPeso = this.formBuilder.group({
      fecha_registro_peso: ['', Validators.required],
      valor_peso: ['', Validators.required],
      comentario_peso: ['']
    });



    this.getNotas();
    this.getServicios();
    this.getCarrito();
  } // CIERRA ON INIT
  public aFormFichaMedica(){
    this.router.navigateByUrl("/auth/registro3usuario")

  }
  toggleForm(): void {
    this.showForm = !this.showForm;
  }

  toggleFormTensionArterial(): void {
    this.showFormTensionArterial = !this.showFormTensionArterial;
  }

  toggleFormRegistroPeso(): void { // Método para controlar la visibilidad del formulario de registro de peso
    this.showFormRegistroPeso = !this.showFormRegistroPeso;
  }

  // METODO PARA AGREGAR NOTA DE GLUCEMIA
  // METODO PARA AGREGAR NOTA DE GLUCEMIA
  agregarNotaGlucemia(): void {
    if (this.formNotasGlucemia.valid) {
      this.paciente.nuevaNotaGlucemia({

        fecha: this.formNotasGlucemia.value.fecha,
        valor: this.formNotasGlucemia.value.valor,
        comentario: this.formNotasGlucemia.value.comentario

      },this.idFichaMedica).subscribe((data: any) => {
        //alert('Nota de glucemia registrada');
        console.log("Datos registrados de glucemia");
        console.log(data);
        this.getNotas();
        this.formNotasGlucemia.reset();
      },
      (error: any) => {
        console.log("Datos de glucemia no fueron registrados ");
        //console.log("El id del paciente es:");
        //console.log(this.getId);
        //console.log("Los datos del paciente son:");
        //console.log(this.getUser);
        //console.log("Las fichas medicas son:");
        //console.log(this.getId);

        console.log(error);
      }
    
    
    );
    } else {
      alert('Ingrese los datos correctamente');
      this.formNotasGlucemia.markAllAsTouched();
    }
  }

    
    // METODO PARA AGREGAR NOTA DE ARTERIAL 
    // METODO PARA AGREGAR NOTA DE ARTERIAL 
  agregarTensionArterial(): void {
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

    // METODO PARA AGREGAR NOTA DE PESO
    // METODO PARA AGREGAR NOTA DE PESO
  agregarRegistroPeso(): void {
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

  // GET DE LAS NOTAS DE GLUCEMIA
  getNotas(): void {
    this.paciente.muestraNotasUsuario().subscribe({
      next: (notas_S) => {
        this.notas_glucemia = notas_S;
      },
      error: (errorData) => {
        console.error(errorData);
      }
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

  eliminarServ(id:string){
    this.paciente.DELETE_SERV(id).subscribe(()=>{
      alert("Servicio eliminado del carrito")
      this.getCarrito()
      console.log("el id de delete es"+id)
    })
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
}
