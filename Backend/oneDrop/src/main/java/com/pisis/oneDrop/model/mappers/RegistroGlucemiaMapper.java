package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaReadDto;
import com.pisis.oneDrop.model.entities.RegistroGlucemia;
import org.springframework.stereotype.Component;

@Component
public class RegistroGlucemiaMapper {
    public RegistroGlucemia toEntity (RegistroGlucemiaAddDto addDto){
        return RegistroGlucemia.builder()
                .fecha(addDto.getFecha())
                .valor(addDto.getValor())
                .comentario(addDto.getComentario())
                .build();
    }

    public RegistroGlucemiaReadDto toReadDto (RegistroGlucemia entity){
        return RegistroGlucemiaReadDto.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .valor(entity.getValor())
                .comentario(entity.getComentario())
                .build();
    }


}
