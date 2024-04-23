package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.model.dtos.servicios.ServicioAddDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioReadDto;
import com.pisis.oneDrop.model.entities.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ServicioMapper {
    public Servicio toEntity (ServicioAddDto addDto){
        return Servicio.builder()
                .nombre(addDto.getNombre())
                .descripcion(addDto.getDescripcion())
                .precio(addDto.getPrecio())
                .comentarios(addDto.getComentarios())
                .build();
    }
    public ServicioReadDto toReadDto (Servicio entity){
        return ServicioReadDto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .comentarios(entity.getComentarios())
                .build();
    }
}
