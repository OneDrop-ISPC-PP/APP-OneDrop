package com.pisis.oneDrop.model.entities;


import com.pisis.oneDrop.auth.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne
    @JoinColumn(name="id_paciente", referencedColumnName="id")
    private User paciente;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Servicio> servicios = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<ResumenCompra> historialCompras = new ArrayList<>();

}
