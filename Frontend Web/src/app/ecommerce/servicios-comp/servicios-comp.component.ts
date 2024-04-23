import { Component, OnInit } from '@angular/core';
import { EcommerceServiceService } from 'src/app/servicios/ecommerce-service.service';

@Component({
  selector: 'app-servicios-comp',
  templateUrl: './servicios-comp.component.html',
  styleUrls: ['./servicios-comp.component.css']
})
export class ServiciosCompComponent implements OnInit {
  servicios:any


    constructor(private servicio:EcommerceServiceService){

    //////// MUESTRA SERVICIOS ////////
      this.servicio.muestraservicio().subscribe({
        next:(servicios_S)=>{
          this.servicios=servicios_S
        },
        error:(errorData)=>{
          console.error(errorData);
        }
        
      }
         )
    }


    ///////  CODIGO PARA AGREGAR AL CARRITO  ////////
    Snombre:string=""
    agregarNombre(value:string){
      this.Snombre=value

      return this.Snombre
    }

    Smonto:string=""
    agregarMonto(value2:string){
      this.Smonto=value2
      return this.Smonto
    }

    nuevoPedido:any[]=[]
    nuevoServicio:any[]=[]

    nuevoCarrito(){
      /* La idea es guardar en un array los servicios que se van selccionando */ 
      this.nuevoServicio.push(this.Snombre)
      this.nuevoServicio.push(this.Smonto)

      this.nuevoPedido.push(this.nuevoServicio)

      return this.nuevoPedido
    }





    ngOnInit():void{}
}


