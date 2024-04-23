package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaAddDto;
import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaReadDto;
import com.pisis.oneDrop.model.entities.FichaMedica;
import org.springframework.stereotype.Component;

@Component
public class FichaMedicaMapper {

    public FichaMedica toEntity (FichaMedicaAddDto addDto){
        return FichaMedica.builder()
                .tipo_diabetes(addDto.getTipo_diabetes())
                .terapia_insulina(addDto.getTerapia_insulina())
                .terapia_pastillas(addDto.getTerapia_pastillas())
                .tipo_glucometro(addDto.getTipo_glucometro())
                .tipo_sensor(addDto.getTipo_sensor())
                .objetivo_glucosa(addDto.getObjetivo_glucosa())
                .comorbilidades(addDto.getComorbilidades())
                .build();
    }

    public FichaMedicaReadDto toReadDto (FichaMedica entity){
        return FichaMedicaReadDto.builder()
                .id(entity.getId())
                .tipo_diabetes(entity.getTipo_diabetes())
                .terapia_insulina(entity.getTerapia_insulina())
                .terapia_pastillas(entity.getTerapia_pastillas())
                .tipo_glucometro(entity.getTipo_glucometro())
                .tipo_sensor(entity.getTipo_sensor())
                .objetivo_glucosa(entity.getObjetivo_glucosa())
                .comorbilidades(entity.getComorbilidades())
                .build();
    }
}
