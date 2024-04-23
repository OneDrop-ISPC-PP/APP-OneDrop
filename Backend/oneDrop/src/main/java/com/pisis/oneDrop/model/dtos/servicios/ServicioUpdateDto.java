package com.pisis.oneDrop.model.dtos.servicios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioUpdateDto {
    private String nombre;
    private String descripcion;
    private Double precio;
    private String comentarios;
}
