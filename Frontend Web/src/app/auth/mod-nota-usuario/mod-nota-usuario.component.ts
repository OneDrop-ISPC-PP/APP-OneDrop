import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router,Params } from '@angular/router';
import { EstadisUsuariosService } from 'src/app/servicios/estadis-usuarios.service';


@Component({
  selector: 'app-mod-nota-usuario',
  templateUrl: './mod-nota-usuario.component.html',
  styleUrls: ['./mod-nota-usuario.component.css']
})
export class ModNotaUsuarioComponent implements OnInit {
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

    this.serv_estadistica.modificar(this.dataid).subscribe((data)=>{
      this.nota=data;

    })

  }

  ///////////////////////////////////////////////////////////////
  update(){
    this.serv_estadistica.modificar2(this.nota,this.dataid).subscribe(()=>{
      this.router.navigate(["auth/dash_user"])

    })


  }




}
 