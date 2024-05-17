package com.pisis.oneDrop.model.mappers;

import com.pisis.oneDrop.model.dtos.resumenCompra.ResumenCompraReadDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioAddDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioReadDto;
import com.pisis.oneDrop.model.entities.ResumenCompra;
import com.pisis.oneDrop.model.entities.Servicio;
import org.springframework.stereotype.Component;

@Component
public class ResumenCompraMapper {
    /*
    private Integer id;
    private Double importe;
    private Date fecha;
    private List<Servicio> servicios = new ArrayList<>();
    private MetodoDePago metodoDePago;

    public ResumenCompra toEntity (ResumenCompraAddDto addDto){
        return ResumenCompra.builder()
                .nombre(addDto.getNombre())
                .descripcion(addDto.getDescripcion())
                .precio(addDto.getPrecio())
                .comentarios(addDto.getComentarios())
                .build();
    }
     */
    public ResumenCompraReadDto toReadDto (ResumenCompra entity){
        return ResumenCompraReadDto.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .importe(entity.getImporte())
                .metodoDePago(entity.getMetodoDePago())
                .servicios(entity.getServicios())
                .build();
    }

}
