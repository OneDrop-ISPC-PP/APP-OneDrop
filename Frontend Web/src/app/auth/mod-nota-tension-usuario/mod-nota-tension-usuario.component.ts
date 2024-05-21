import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router,Params } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';

@Component({
  selector: 'app-mod-nota-tension-usuario',
  templateUrl: './mod-nota-tension-usuario.component.html',
  styleUrls: ['./mod-nota-tension-usuario.component.css']
})
export class ModNotaTensionUsuarioComponent implements OnInit {
  dataid!: number;
  nota:any;


//////////////////////////////////////////////////////////////////
  constructor(
    private routerAct: ActivatedRoute,
    private router: Router,
    private serv_estadistica:EstadisUsuariosService

  ){}

//////////////////////////////////////////////////////////////////
  ngOnInit(): void {
    this.routerAct.paramMap.subscribe((param:Params) =>{
      this.dataid=param['get']("id");
      console.log("El id es:"+this.dataid);

    })

    this.serv_estadistica.GET_NOTA_TENSION_POR_ID(this.dataid).subscribe(
      (data)=>{
        this.nota=data;
        console.log("los datos de la nota de tension son:");
        console.log(data);
  

      },
      (error)=>{
        console.log("No se pudieron cargar los datos de la nota de tension");
        console.log(error)
      
      })

    this.serv_estadistica.modificar(this.dataid).subscribe((data)=>{
      this.nota=data;

    })

  }

  ///////////////////////////////////////////////////////////////
  update(){
    this.serv_estadistica.UPDATE_NOTA_TENSION(this.nota,this.dataid).subscribe(
      (data)=>{
      this.router.navigate(["auth/dash_user"])
      console.log("La nota se modifico con exito")
      console.log(data)},

      (error) => {
        console.log("La nota NO se modifico con exito")
        console.log(error)}
      
    )


  }




}
 
