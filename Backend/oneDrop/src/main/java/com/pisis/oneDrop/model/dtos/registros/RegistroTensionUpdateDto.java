package com.pisis.oneDrop.model.dtos.registros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroTensionUpdateDto {
    private Date fecha;
    private Integer diastolica;
    private Integer sistolica;
    private String comentario;
}
