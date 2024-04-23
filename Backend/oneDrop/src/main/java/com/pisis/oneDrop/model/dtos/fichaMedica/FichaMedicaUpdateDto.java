package com.pisis.oneDrop.model.dtos.fichaMedica;

import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.model.entities.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaMedicaUpdateDto {
    private Tipo_diabetes tipo_diabetes;
    private Terapia_insulina terapia_insulina;
    private Terapia_pastillas terapia_pastillas;
    private Tipo_glucometro tipo_glucometro;
    private Tipo_sensor tipo_sensor;
    private String objetivo_glucosa;
    private String comorbilidades;
}
