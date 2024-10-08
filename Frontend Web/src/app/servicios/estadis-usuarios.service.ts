import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpHeaders } from '@angular/common/http';
import { BACKEND_URL } from '../app.config';

// INTERFACE DE NOTAS USUARIO
import { NotasGlucemia } from './interfaces/notas-glucemia';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EstadisUsuariosService {

  constructor(private http: HttpClient) { }

  // URL GET PARA FICHA MEDICA POR UD USER:
  url_GET_FICHA_MEDICA: string = BACKEND_URL+'/fichaMedica/user/';

  // -------- GLUCEMIA ----------
  url_NOTAS_GLUCEMIA_GET: string = BACKEND_URL+'/registros/glucemia/usuario/';
  url_NOTA_GLUCEMIA_GET: string = BACKEND_URL+'/registros/glucemia/';
  url_NOTA_GLUCEMIA_POST: string = BACKEND_URL+'/registros/glucemia/usuario/';
  url_NOTA_GLUCEMIA_PUT: string = BACKEND_URL+'/registros/glucemia/';
  url_NOTA_GLUCEMIA_DEL: string = BACKEND_URL+'/registros/glucemia/';


  // -------- PESO ----------
  url_NOTAS_PESO_GET: string = BACKEND_URL+'/registros/peso/usuario/';
  url_NOTA_PESO_GET: string = BACKEND_URL+'/registros/peso/';
  url_NOTA_PESO_POST: string = BACKEND_URL+'/registros/peso/usuario/';
  url_NOTA_PESO_PUT: string = BACKEND_URL+'/registros/peso/';
  url_NOTA_PESO_DEL: string = BACKEND_URL+'/registros/peso/';


  // -------- TENSION ----------
  url_NOTAS_TENSION_GET: string = BACKEND_URL+'/registros/tensionArterial/usuario/';
  url_NOTA_TENSION_GET: string = BACKEND_URL+'/registros/tensionArterial/';
  url_NOTA_TENSION_POST: string = BACKEND_URL+'/registros/tensionArterial/usuario/';
  url_NOTA_TENSION_PUT: string = BACKEND_URL+'/registros/tensionArterial/';
  url_NOTA_TENSION_DEL: string = BACKEND_URL+'/registros/tensionArterial/';



  // -------- SERVICIOS ----------
  url_SERVICIOS: string = BACKEND_URL+'/servicios/';


  // -------- CARRITO ----------
  url_CARRITO_GET: string = BACKEND_URL+'/carrito/';
  url_CARRITO_POST: string = BACKEND_URL+'/carrito/';

  // -------- CARRITO ----------
  url_CARRITO_ID_USER_GET: string = BACKEND_URL+'/carrito/user/';
  url_CARRITO_ID_USER_POST: string = BACKEND_URL+'/carrito/user/';

  // -------- CARRITO SERVICIOS ----------
  url_CARRITO_SERVICIOS_GET: string = BACKEND_URL+'/carrito/';
  url_CARRITO_SERVICIOS_POST: string = BACKEND_URL+'/carrito/';

  // -------- PDF ----------
  url_PDF_GET: string = BACKEND_URL+'/reportes/';



  ////////////////// GET FICHA MEDICA ///////////////////
  getFichaMedicaIdUser(id:any){
    return this.http.get(this.url_GET_FICHA_MEDICA+id)
  }



  ////////////////// CODIGO NOTAS GLUCEMIA (FUNCIONAL) ///////////////////
  // GET_NOTAS_GLUCEMIA
  public GET_NOTAS_GLUCEMIA(id:any){
    return this.http.get(this.url_NOTAS_GLUCEMIA_GET+id)
  }
  // GET_NOTA_GLUCEMIA POR ID
  public GET_NOTA_GLUCEMIA_POR_ID(id:any){
    return this.http.get(this.url_NOTA_GLUCEMIA_GET+id)
  } 
  // AGREGA NOTAS DE GLUCEMIA
  nuevaNotaGlucemia(datos: any, id:any) {
    return this.http.post(this.url_NOTA_GLUCEMIA_POST+id, datos);
  }
  // UPDATE_NOTAS_GLUCEMIA
  public UPDATE_NOTA_GLUCEMIA(datos:any, id:any){
    return this.http.put(this.url_NOTA_GLUCEMIA_PUT+id,datos)
  }
  // ELIMINAR_NOTAS_GLUCEMIA
  public DELETE_NOTA_GLUCEMIA( id:any){
    return this.http.delete(this.url_NOTA_GLUCEMIA_DEL+id) 
  }


  ////////////////// CODIGO NOTAS PESO (FUNCIONAL) ///////////////////

  // GET_NOTAS_PESO
  public GET_NOTAS_PESO(id:any){
    return this.http.get(this.url_NOTAS_PESO_GET+id)
  }
 // GET_NOTA_PESO POR ID
  public GET_NOTA_PESO_POR_ID(id:any){
    return this.http.get(this.url_NOTA_PESO_GET+id)
  } 
  // AGREGAR NOTA PESO
  nuevaNotaPeso(datos: any, id:any) {
    return this.http.post(this.url_NOTA_PESO_POST+id, datos);
  }
  // ELIMINA NOTA PESO
  public DELETE_NOTA_PESO(id:any){
    return this.http.delete(this.url_NOTA_PESO_DEL+id) 
  }
  // ACTUALIZAR NOTA PESO
  public UPDATE_NOTA_PESO(datos:any, id:any){
    return this.http.put(this.url_NOTA_PESO_PUT+id,datos)
  }



  ////////////////// CODIGO NOTAS TENSION ARTERIAL ///////////////////
  // GET_NOTAS_GLUCEMIA
  public GET_NOTAS_TENSION(id:any){
    return this.http.get(this.url_NOTAS_TENSION_GET+id)
  }
  // GET_NOTA_TENSION POR ID
  public GET_NOTA_TENSION_POR_ID(id:any){
    return this.http.get(this.url_NOTA_TENSION_GET+id)
  }
  // AGREGA NOTAS DE TENSION
  nuevaNotaTension(datos: any, id:any){
    return this.http.post(this.url_NOTA_TENSION_POST+id, datos);
  }
  // ELIMINAR_NOTAS_GLUCEMIA
  public DELETE_NOTA_TENSION( id:any){
    return this.http.delete(this.url_NOTA_TENSION_DEL+id) 
  }
  // ACTUALIZAR NOTA TENSION
  public UPDATE_NOTA_TENSION(datos:any, id:any){
    return this.http.put(this.url_NOTA_TENSION_PUT+id,datos)
  }


////////////////// CODIGO SERVICIOS (FUNCIONAL) ///////////////////
public GET_SERVICIOS(id:any){
  return this.http.get(this.url_SERVICIOS)
}


////////////////// CODIGO CARRITO (FUNCIONAL) ////////////////////
public nuevoCarrito24(datos:any,id:any){
  return this.http.post(this.url_CARRITO_POST+id,datos)
}

// METODO GET DEL CARRITO POR ID
public getCarritoPorIdCarrito(id:any){
  return this.http.get(this.url_CARRITO_GET+id)
}

////////////////// GET FICHA MEDICA ///////////////////
getCarritoPorIdUser(id:any){
  return this.http.get(this.url_CARRITO_ID_USER_GET+id)
}  

// METODO GET DE SERVICIO POR ID - SIRVE PARA TRAER SERVICIO DE LA LISTA
// PARA LUEGO LLEVARLO AL CARRITO
public getServicioPorId(id:any){
  return this.http.get(this.url_SERVICIOS+id)
}

// METODO POST PARA AGREGAR UN SERVIVIO AL CARRITO
public postServicioEnCarrito(data:any,id_car:any, id_Serv:any){
  return this.http.post(this.url_CARRITO_SERVICIOS_POST+id_car+"/"+id_Serv, data)
}
// METODO POST PARA ELIMINAR UN SERVIVIO DEL CARRITO

public delServicioEnCarrito(id_carr:any, id_Serv:any){
  return this.http.delete(this.url_CARRITO_SERVICIOS_POST+id_carr+"/"+id_Serv)
}

////////// DESCARGAR PDF //////////
////////// DESCARGAR PDF //////////
public descargarDPF(id:any){
  return this.http.get(this.url_PDF_GET+id, {responseType:'blob'});

}



////////////////////////////////////////////////////
////////////////////////////////////////////////////
////////////////////////////////////////////////////

  // MUESTRA LAS NOTAS DE GLUCEMIA (BORRAR SI ANDA EL DE ARRIBA)
  muestraNotasUsuario() {
    return this.http.get(this.url_NOTAS_GLUCEMIA_GET, { withCredentials: true });
  }

  // MODIFICAR NOTAS DE GLUCEMIA
  // Método para TRAER la informacion
  modificar(id: number) {
    return this.http.get(this.url_NOTAS_GLUCEMIA_GET + id, { withCredentials: true });
  }

  // Método para MODIFICAR la informacion
  modificar2(datos: any, id: number) {
    return this.http.put(this.url_NOTAS_GLUCEMIA_GET + id, datos, { withCredentials: true });
  }

  // Método para ELIMINAR la informacion
  DELETE(id: string) {
    return this.http.delete(this.url_NOTAS_GLUCEMIA_GET + id, { withCredentials: true });
  }

  ////////////////// CODIGO TENSION ARTERIAL ///////////////////
  
  // Método para agregar registro de tensión arterial
  agregarTensionArterial(datos: any, id:any) {
    return this.http.post(BACKEND_URL+'/registros/tensionArterial/usuario/'+id, datos, { withCredentials: true });
  }

  // Método para obtener registros de tensión arterial de un usuario específico
  obtenerRegistrosTensionArterial(idUsuario: number) {
    return this.http.get(`${BACKEND_URL}/registros/tensionArterial/usuario/${idUsuario}`, { withCredentials: true });
  }

  // Método para actualizar un registro de tensión arterial existente
  actualizarRegistroTensionArterial(idRegistro: number, datosActualizados: any) {
    return this.http.put(`${BACKEND_URL}/registros/tensionArterial/${idRegistro}`, datosActualizados, { withCredentials: true });
  }

  // Método para eliminar un registro de tensión arterial
  eliminarRegistroTensionArterial(idRegistro: number) {
    return this.http.delete(`${BACKEND_URL}/registros/tensionArterial/${idRegistro}`, { withCredentials: true });
  }

  ////////////////// CODIGO PESO ///////////////////
// URL de los registros de peso
urlRegistrosPeso: string = BACKEND_URL+'/registros/peso/usuario/1';

// Método para obtener los registros de peso de un usuario específico
obtenerRegistrosPeso(idUsuario: number) {
  return this.http.get(`${BACKEND_URL}/registros/peso/usuario/${idUsuario}`, { withCredentials: true });
}

// Método para agregar un nuevo registro de peso
agregarRegistroPeso(registroPeso: any) {
  return this.http.post(this.urlRegistrosPeso, registroPeso, { withCredentials: true });
}

// Método para actualizar un registro de peso existente
actualizarRegistroPeso(idRegistro: number, datosActualizados: any) {
  return this.http.put(`${BACKEND_URL}/registros/peso/${idRegistro}`, datosActualizados, { withCredentials: true });
}

// Método para eliminar un registro de peso
eliminarRegistroPeso(idRegistro: number) {
  return this.http.delete(`${BACKEND_URL}/registros/peso/${idRegistro}`, { withCredentials: true });
}




  ////////////////// CODIGO DEL CARRITO ///////////////////
  
  // MUESTRA LOS SERVICIOS DISPONIBLES A LOS USUARIOS
  muestraServicioAUsuario() {
    return this.http.get<NotasGlucemia[]>(this.url_SERVICIOS, { withCredentials: true });
  }

  // MUESTRA CARRITO USUARIOS
  muestraCarritoAUsuario() {
    return this.http.get(this.url_CARRITO_GET, { withCredentials: true });
  }

  // AGREGA SERVICIO AL CARRITO
  agregaAlCarrito(servicio: any) {
    return this.http.post(this.url_CARRITO_POST, servicio, { withCredentials: true });
  }

  // ELIMINA SERVICIO AL CARRITO
  DELETE_SERV(id: string) {
    return this.http.delete(this.url_CARRITO_GET+ id, { withCredentials: true });
  }
}
