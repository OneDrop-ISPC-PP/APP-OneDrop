package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.model.dtos.registros.RegistroAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroReadDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroTensionAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroTensionReadDto;
import com.pisis.oneDrop.model.entities.RegistroTensionArterial;
import org.springframework.stereotype.Component;

@Component
public class RegistroTensionArterialMapper {
    public RegistroTensionArterial toEntity (RegistroTensionAddDto addDto){
        return RegistroTensionArterial.builder()
                .fecha(addDto.getFecha())
                .diastolica(addDto.getDiastolica())
                .sistolica(addDto.getSistolica())
                .comentario(addDto.getComentario())
                .build();
    }

    public RegistroTensionReadDto toReadDto (RegistroTensionArterial entity){
        return RegistroTensionReadDto.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .diastolica(entity.getDiastolica())
                .sistolica(entity.getSistolica())
                .comentario(entity.getComentario())
                .build();
    }
}
