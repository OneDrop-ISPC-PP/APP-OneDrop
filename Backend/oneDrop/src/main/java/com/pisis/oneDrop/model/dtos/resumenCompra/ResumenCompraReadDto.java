package com.pisis.oneDrop.model.dtos.resumenCompra;

import com.pisis.oneDrop.model.entities.Servicio;
import com.pisis.oneDrop.model.entities.enums.MetodoDePago;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResumenCompraReadDto {
    private Integer id;
    private Double importe;
    private Date fecha;
    private List<Servicio> servicios = new ArrayList<>();
    private MetodoDePago metodoDePago;
}
