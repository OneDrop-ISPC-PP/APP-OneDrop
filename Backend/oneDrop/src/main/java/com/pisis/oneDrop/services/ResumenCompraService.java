package com.pisis.oneDrop.services;

import com.pisis.oneDrop.model.dtos.resumenCompra.ResumenCompraReadDto;
import com.pisis.oneDrop.model.entities.Carrito;
import com.pisis.oneDrop.model.entities.ResumenCompra;
import com.pisis.oneDrop.model.entities.enums.MetodoDePago;
import com.pisis.oneDrop.model.mappers.ResumenCompraMapper;
import com.pisis.oneDrop.model.repositories.ResumenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ResumenCompraService {
    @Autowired
    ResumenCompraMapper resumenCompraMapper;
    @Autowired
    ResumenCompraRepository resumenRepository;

    public ResumenCompra crearResumen (Carrito carrito, MetodoDePago metodoDePago){
        Date fechaCompra = new Date();
        Double importe = 0.0;
        for (int i =0; i<carrito.getServicios().size(); i++){
            importe+=carrito.getServicios().get(i).getPrecio();
        }
        MetodoDePago formaDePago = metodoDePago == null? MetodoDePago.EFECTIVO : metodoDePago;
        ResumenCompra resumenCompra = ResumenCompra.builder()
                .fecha(fechaCompra)
                .importe(importe)
                .metodoDePago(formaDePago)
                .servicios(new ArrayList<>(carrito.getServicios())) // copiar lista, para evitar referencias compartidas
                .build();
        return resumenRepository.save(resumenCompra);
    }
}
