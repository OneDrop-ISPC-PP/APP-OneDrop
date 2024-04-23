import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Emitters } from './emitters/emitters';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'onedrop-app';
  authenticated = false;
  message: string="";


//////////////////////////////////////////////////
  constructor(private http: HttpClient) {}

  
////////////////////////////////////////////////////
  ngOnInit(): void {
    Emitters.authEmitter.subscribe(
      (auth: boolean) => {
        this.authenticated = auth;
      }
    );


    this.http.get('http://localhost:4200/', {withCredentials: true}).subscribe(
      (res: any) => {
        this.message = `Bienvenido`;
        Emitters.authEmitter.emit(true);
      },
    );
  }

  


  /////////////////////////////////////////////////
  logout(): void {
    this.http.post('http://localhost:8000/api/auth/logout/', {}, {withCredentials: true})
      .subscribe(() => this.authenticated = false);
  }
}
