package com.pisis.oneDrop.model.dtos.servicios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioReadDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String comentarios;
}


