package com.pisis.oneDrop.model.dtos.carrito;

import com.pisis.oneDrop.auth.dtos.UserReadDto;
import com.pisis.oneDrop.model.dtos.resumenCompra.ResumenCompraReadDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CarritoReadDto {
    private Integer id;
    private UserReadDto paciente;
    private List<ServicioReadDto> servicios;
    private List<ResumenCompraReadDto> historialCompras;
}
