package com.pisis.oneDrop.model.dtos.resumenCompra;

import com.pisis.oneDrop.model.entities.Servicio;
import com.pisis.oneDrop.model.entities.enums.MetodoDePago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResumenCompraAddDto {
    private Double importe;
    private Date fecha;
    private List<Servicio> servicios;
    private MetodoDePago metodoDePago;
}
