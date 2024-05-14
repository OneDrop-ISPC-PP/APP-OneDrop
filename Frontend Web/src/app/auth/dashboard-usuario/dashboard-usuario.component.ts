import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';

@Component({
  selector: 'app-dashboard-usuario',
  templateUrl: './dashboard-usuario.component.html',
  styleUrls: ['./dashboard-usuario.component.css']
})
export class DashboardUsuarioComponent implements OnInit {
  notas_glucemia: any;
  servicios: any;
  formNotasPOST: FormGroup;
  formActualizarNota: FormGroup;
  formTensionArterial: FormGroup;
  formRegistroPeso: FormGroup; // Formulario de registro de peso
  serviciosCarrito: any[] = [];
  Snombre: string = '';
  Smonto: string = '';
  nuevoPedido: any[] = [];
  showForm: boolean = false;
  showFormTensionArterial: boolean = false;
  showFormRegistroPeso: boolean = false; // Propiedad para controlar el formulario de registro de peso

  constructor(
    private paciente: EstadisUsuariosService,
    private formBuilder: FormBuilder,
    private router: Router
  ) {
    this.formNotasPOST = this.formBuilder.group({
      fecha_registro: ['', Validators.required],
      valor_glucemia: ['', [Validators.required, Validators.maxLength(3)]],
      comentario_registro: ['', Validators.required]
    });

    this.formActualizarNota = this.formBuilder.group({
      fecha_registro: ['', Validators.required],
      valor_glucemia: ['', [Validators.required, Validators.maxLength(3)]],
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
  }

  ngOnInit(): void {
    this.getNotas();
    this.getServicios();
    this.getCarrito();
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

  agregarNota(): void {
    if (this.formNotasPOST.valid) {
      this.paciente.nuevaNota({
        fecha_registro: this.formNotasPOST.value.fecha_registro,
        valor_glucemia: this.formNotasPOST.value.valor_glucemia,
        comentario_registro: this.formNotasPOST.value.comentario_registro
      }).subscribe((respuesta: any) => {
        alert('Nota registrada');
        this.getNotas();
        this.formNotasPOST.reset();
      });
    } else {
      alert('Ingrese los datos correctamente');
      this.formNotasPOST.markAllAsTouched();
    }
  }

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

  // Método para agregar el registro de peso
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
