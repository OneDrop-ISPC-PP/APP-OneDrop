package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.auth.UserMapper;
import com.pisis.oneDrop.model.dtos.carrito.CarritoReadDto;
import com.pisis.oneDrop.model.dtos.resumenCompra.ResumenCompraReadDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioReadDto;
import com.pisis.oneDrop.model.entities.Carrito;
import com.pisis.oneDrop.model.entities.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarritoMapper {
    @Autowired
    UserMapper userMapper;

    @Autowired
    ServicioMapper servicioMapper;
    @Autowired
    ResumenCompraMapper resumenCompraMapper;
    /*
    public Carrito toEntity (CarritoAddDto addDto){
        return Carrito.builder()
                .paciente(addDto.getPaciente())
                .servicios(addDto.getServicios())
                .historialCompras(addDto.getHistorialCompras())
                .build();
    }
    */
    public CarritoReadDto toReadDto (Carrito entity){
        List<ServicioReadDto> serviciosReadDtos = entity.getServicios().stream().map(servicio -> servicioMapper.toReadDto(servicio)).collect(Collectors.toList());
        List<ResumenCompraReadDto> resumenesReadDtos = entity.getHistorialCompras().stream().map(resumenCompra -> resumenCompraMapper.toReadDto(resumenCompra)).collect(Collectors.toList());
        return CarritoReadDto.builder()
                .paciente(userMapper.toReadDto(entity.getPaciente()))
                .servicios(serviciosReadDtos)
                .historialCompras(resumenesReadDtos)
                .build();
    }
}
