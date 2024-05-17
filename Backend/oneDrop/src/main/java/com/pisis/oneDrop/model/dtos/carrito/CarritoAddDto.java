package com.pisis.oneDrop.model.dtos.carrito;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarritoAddDto {
    private Integer idPaciente; //Solo para crear el carrito, solo necesito ID.. Es necesario crear el CarritoAddDto???
    // SE SUPONE QUE UN ADD DTO NO TRAE COSAS CARGADOS !!private List<Servicio> servicios;
    // SE SUPONE QUE UN ADD DTO NO TRAE COSAS CARGADOS !!private List<ResumenCompra> historialCompras;
}
