package com.pisis.oneDrop.model.dtos.registros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroTensionAddDto {
    @NotNull
    private Date fecha;

    @NotNull(message = "Valor no puede ser nulo")
    @PositiveOrZero(message = "El valor de debe ser mayor a 0")
    private Integer diastolica;

    @NotNull(message = "Valor no puede ser nulo")
    @PositiveOrZero(message = "El valor de debe ser mayor a 0")
    private Integer sistolica;

    private String comentario;
}
