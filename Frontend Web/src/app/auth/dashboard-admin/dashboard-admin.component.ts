import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EstadisAdminsService } from 'src/app/servicios/estadis-admins.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AuthService } from 'src/app/servicios/auth.service';


@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.css']
})
export class DashboardAdminComponent implements OnInit{

  ngOnInit(): void {}  // CIERRA NG ONIT

 public seleccionado(){
  let seleccionado = "border: #2845d6 1px solid;";
 }

seleccionado2 = "border: #2845d6 1px solid;";

}
  




