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
public class RegistroUpdateDto {
    private Date fecha;
    private Double valor;
    private String comentario;
}
