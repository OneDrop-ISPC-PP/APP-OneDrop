import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';
import { AuthService } from 'src/app/servicios/auth.service';

@Component({
  selector: 'app-dashboard-usuario',
  templateUrl: './dashboard-usuario.component.html',
  styleUrls: ['./dashboard-usuario.component.css']
})
export class DashboardUsuarioComponent implements OnInit {
  notas_glucemia: any;
  servicios: any;
  formNotasPOST: FormGroup;

  // Variables para el carrito
  Snombre: string = '';
  Smonto: string = '';
  nuevoPedido: any[] = [];

  serviciosCarrito: any;

/////////////////////////////////////////////////
  constructor(
    private paciente: EstadisUsuariosService,
    private usuario: AuthService,
    private formBuilder: FormBuilder,
    private router: Router,
    private routerAct: ActivatedRoute,
    private http: HttpClient
  ) {
    this.formNotasPOST = this.formBuilder.group({
      fecha_registro: ['', Validators.required],
      valor_glucemia: ['', [Validators.required, Validators.maxLength(3)]],
      comentario_registro: ['', Validators.required]
    });
  }
////////////////////////////////////////////////
  ngOnInit(): void {
    this.getNotas();
    this.getServicios();
    this.getCarrito();

    // Codigo que muestra las notas (instancia)
    this.paciente.muestraNotasUsuario().subscribe({
      next:(notas_G)=>{
        this.notas_glucemia=notas_G
      },
      error:(errorData)=>{
        console.error(errorData);
      } 
    });

    // Codigo que muestra los servicios (instancia)
    this.paciente.muestraServicioAUsuario().subscribe({
      next:(servicio)=>{
        this.servicios=servicio
      },
      error:(errorData)=>{
        console.error(errorData);
      } 
    })    

  }
////////////////////////////////////////////////

  //////////// CODIGO NOTAS ///////////////
  //////////// CODIGO NOTAS ///////////////
  //////////// CODIGO NOTAS ///////////////

  // AGREGAR NOTA
  agregarNota(): void {
    if (this.formNotasPOST.valid) {
      this.paciente.nuevaNota(
        {
          fecha_registro: this.formNotasPOST.value.fecha_registro,
          valor_glucemia: this.formNotasPOST.value.valor_glucemia,
          comentario_registro: this.formNotasPOST.value.comentario_registro
        })
        .subscribe((respuesta: any) => {
          alert('Nota registrada');
          this.getNotas();
          this.formNotasPOST.reset();
        });
    } else {
      alert('Ingrese los datos correctamente');
      this.formNotasPOST.markAllAsTouched();
    }
  }

// METODO GET NOTAS
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
// METODO DELETE NOTAS
  eliminar(id: string): void {
    this.paciente.DELETE(id).subscribe(() => {
      alert('Nota Eliminada');
      this.getNotas();
      console.log('el id de delete es' + id);
    });
  }



  //////////////// CODIGO DEL CARRITO /////////////////
  //////////////// CODIGO DEL CARRITO /////////////////
  //////////////// CODIGO DEL CARRITO /////////////////
  getCarrito(){
    this.paciente.muestraCarritoAUsuario().subscribe({
      next: (servicios_C) => {
        this.serviciosCarrito = servicios_C;
      },
      error: (errorData) => {
        console.error(errorData);
      }
    });

  }
  // METODO AGREGA AL CARRITO
  agregarCarrito(servicio: any): void {
    this.paciente.agregaAlCarrito(servicio)
      .subscribe((data) => {
        console.log(data);
        this.getCarrito();
      });
  }

  // METODO DELETE
  eliminarServ(id:string){
      this.paciente.DELETE_SERV(id).subscribe(()=>{
        alert("Servicio eliminado del carrito")
        this.getCarrito()
        console.log("el id de delete es"+id)
      })
    }

  // METODO GET DE SERVICIOS
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
    
    
  ///////////////////////////////////////////
  ///////////////////////////////////////////
  ///////////////////////////////////////////
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
