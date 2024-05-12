package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.model.dtos.registros.RegistroAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroReadDto;
import com.pisis.oneDrop.model.entities.RegistroPeso;
import org.springframework.stereotype.Component;

@Component
public class RegistroPesoMapper {
    public RegistroPeso toEntity (RegistroAddDto addDto){
        return RegistroPeso.builder()
                .fecha(addDto.getFecha())
                .valor(addDto.getValor())
                .comentario(addDto.getComentario())
                .build();
    }

    public RegistroReadDto toReadDto (RegistroPeso entity){
        return RegistroReadDto.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .valor(entity.getValor())
                .comentario(entity.getComentario())
                .build();
    }
}
