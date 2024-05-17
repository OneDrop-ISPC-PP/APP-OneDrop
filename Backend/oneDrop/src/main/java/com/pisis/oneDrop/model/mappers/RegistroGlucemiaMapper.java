package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.model.dtos.registros.RegistroAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroReadDto;
import com.pisis.oneDrop.model.entities.RegistroGlucemia;
import org.springframework.stereotype.Component;

@Component
public class RegistroGlucemiaMapper {
    public RegistroGlucemia toEntity (RegistroAddDto addDto){
        return RegistroGlucemia.builder()
                .fecha(addDto.getFecha())
                .valor(addDto.getValor())
                .comentario(addDto.getComentario())
                .build();
    }

    public RegistroReadDto toReadDto (RegistroGlucemia entity){
        return RegistroReadDto.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .valor(entity.getValor())
                .comentario(entity.getComentario())
                .build();
    }


}
