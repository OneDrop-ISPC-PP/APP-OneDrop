package com.pisis.oneDrop.model.dtos.fichaMedica;

import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.model.entities.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaMedicaAddDto {
    @NotNull
    private Integer id_paciente;
    @NotNull
    private Tipo_diabetes tipo_diabetes;
    @NotNull
    private Terapia_insulina terapia_insulina;
    @NotNull
    private Terapia_pastillas terapia_pastillas;
    @NotNull
    private Tipo_glucometro tipo_glucometro;
    @NotNull
    private Tipo_sensor tipo_sensor;

    private String objetivo_glucosa;
    private String comorbilidades;
    private Double peso;
}
