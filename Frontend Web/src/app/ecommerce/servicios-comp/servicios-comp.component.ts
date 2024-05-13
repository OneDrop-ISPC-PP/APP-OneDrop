import { Component, OnInit } from '@angular/core';
import { EcommerceServiceService } from 'src/app/servicios/ecommerce-service.service';

@Component({
  selector: 'app-servicios-comp',
  templateUrl: './servicios-comp.component.html',
  styleUrls: ['./servicios-comp.component.css']
})
export class ServiciosCompComponent implements OnInit {
  listaServicios:any


    constructor(private serv_eccomerce:EcommerceServiceService){
      
      //////// MUESTRA SERVICIOS ////////

    }

    // NG ON INIT
      ngOnInit():void{
        this.serv_eccomerce.GET_SERVICIOS().subscribe(
          (data:any)=>{
            this.listaServicios = data.registros;
            console.log("CARGA DE SERVICIOS EXITOSA LOS DATOS SON:")
            console.log(data.registros);
  
          },
          (error) => {
            console.log("ERROR EN LA CARGA DE LOS SERVICIOS")
            console.log(error);
  
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





}


