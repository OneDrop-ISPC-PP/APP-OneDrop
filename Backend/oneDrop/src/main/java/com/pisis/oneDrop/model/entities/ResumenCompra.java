package com.pisis.oneDrop.model.entities;

import com.pisis.oneDrop.model.entities.enums.MetodoDePago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumenCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double importe;

    private Date fecha;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Servicio> servicios = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MetodoDePago metodoDePago;

}
