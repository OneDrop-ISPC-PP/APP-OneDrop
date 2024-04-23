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
public class RegistroGlucemiaAddDto {
    @NotNull
    private Date fecha;

    @NotNull(message = "Glucemia no puede ser nula")
    @PositiveOrZero(message = "El valor de glucemia debe ser mayor a 0")
    private Double valor;

    private String comentario;
}
