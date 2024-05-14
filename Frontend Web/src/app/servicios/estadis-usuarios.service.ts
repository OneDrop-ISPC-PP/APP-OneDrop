import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

// INTERFACE DE NOTAS USUARIO
import { NotasGlucemia } from './interfaces/notas-glucemia';

@Injectable({
  providedIn: 'root'
})
export class EstadisUsuariosService {

  constructor(private http: HttpClient) { }

  // URL DE NOTAS DE GLUCEMIA:
  url_NOTAS_GLUCEMIA: string = 'http://localhost:8080/registros/glucemia/usuario/1';
  // URL DE SERVICIOS:
  url_SERVICIOS: string = 'http://localhost:8080/servicios/';
  // URL DE CARRITO:
  url_CARRITO: string = 'http://localhost:3000/CARRITO/';

  ////////////////// CODIGO NOTAS GLUCEMIA ///////////////////
  
  // MUESTRA LAS NOTAS DE GLUCEMIA
  muestraNotasUsuario() {
    return this.http.get(this.url_NOTAS_GLUCEMIA, { withCredentials: true });
  }

  // AGREGA NOTAS DE GLUCEMIA
  nuevaNota(datos: any) {
    return this.http.post(this.url_NOTAS_GLUCEMIA, datos, { withCredentials: true });
  }

  // MODIFICAR NOTAS DE GLUCEMIA
  // Método para TRAER la informacion
  modificar(id: number) {
    return this.http.get(this.url_NOTAS_GLUCEMIA + id, { withCredentials: true });
  }

  // Método para MODIFICAR la informacion
  modificar2(datos: any, id: number) {
    return this.http.put(this.url_NOTAS_GLUCEMIA + id, datos, { withCredentials: true });
  }

  // Método para ELIMINAR la informacion
  DELETE(id: string) {
    return this.http.delete(this.url_NOTAS_GLUCEMIA + id, { withCredentials: true });
  }

  ////////////////// CODIGO TENSION ARTERIAL ///////////////////
  
  // Método para agregar registro de tensión arterial
  agregarTensionArterial(datos: any) {
    return this.http.post('http://localhost:8080/registros/tensionArterial/usuario/1', datos, { withCredentials: true });
  }

  // Método para obtener registros de tensión arterial de un usuario específico
  obtenerRegistrosTensionArterial(idUsuario: number) {
    return this.http.get(`http://localhost:8080/registros/tensionArterial/usuario/${idUsuario}`, { withCredentials: true });
  }

  // Método para actualizar un registro de tensión arterial existente
  actualizarRegistroTensionArterial(idRegistro: number, datosActualizados: any) {
    return this.http.put(`http://localhost:8080/registros/tensionArterial/${idRegistro}`, datosActualizados, { withCredentials: true });
  }

  // Método para eliminar un registro de tensión arterial
  eliminarRegistroTensionArterial(idRegistro: number) {
    return this.http.delete(`http://localhost:8080/registros/tensionArterial/${idRegistro}`, { withCredentials: true });
  }

  ////////////////// CODIGO PESO ///////////////////
// URL de los registros de peso
urlRegistrosPeso: string = 'http://localhost:8080/registros/peso/usuario/1';

// Método para obtener los registros de peso de un usuario específico
obtenerRegistrosPeso(idUsuario: number) {
  return this.http.get(`http://localhost:8080/registros/peso/usuario/${idUsuario}`, { withCredentials: true });
}

// Método para agregar un nuevo registro de peso
agregarRegistroPeso(registroPeso: any) {
  return this.http.post(this.urlRegistrosPeso, registroPeso, { withCredentials: true });
}

// Método para actualizar un registro de peso existente
actualizarRegistroPeso(idRegistro: number, datosActualizados: any) {
  return this.http.put(`http://localhost:8080/registros/peso/${idRegistro}`, datosActualizados, { withCredentials: true });
}

// Método para eliminar un registro de peso
eliminarRegistroPeso(idRegistro: number) {
  return this.http.delete(`http://localhost:8080/registros/peso/${idRegistro}`, { withCredentials: true });
}




  ////////////////// CODIGO DEL CARRITO ///////////////////
  
  // MUESTRA LOS SERVICIOS DISPONIBLES A LOS USUARIOS
  muestraServicioAUsuario() {
    return this.http.get<NotasGlucemia[]>(this.url_SERVICIOS, { withCredentials: true });
  }

  // MUESTRA CARRITO USUARIOS
  muestraCarritoAUsuario() {
    return this.http.get(this.url_CARRITO, { withCredentials: true });
  }

  // AGREGA SERVICIO AL CARRITO
  agregaAlCarrito(servicio: any) {
    return this.http.post(this.url_CARRITO, servicio, { withCredentials: true });
  }

  // ELIMINA SERVICIO AL CARRITO
  DELETE_SERV(id: string) {
    return this.http.delete(this.url_CARRITO + id, { withCredentials: true });
  }
}
