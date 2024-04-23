package com.pisis.oneDrop.model.entities;

import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.model.entities.enums.*;
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
public class FichaMedica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @OneToOne
    @JoinColumn(name="id_paciente", referencedColumnName="id", nullable = true)
    private User paciente;

    @Enumerated(EnumType.STRING)
    private Tipo_diabetes tipo_diabetes;
    @Enumerated(EnumType.STRING)
    private Terapia_insulina terapia_insulina;
    @Enumerated(EnumType.STRING)
    private Terapia_pastillas terapia_pastillas;
    @Enumerated(EnumType.STRING)
    private Tipo_glucometro tipo_glucometro;
    @Enumerated(EnumType.STRING)
    private Tipo_sensor tipo_sensor;

    private String objetivo_glucosa;
    private String comorbilidades;

    @OneToMany(fetch = FetchType.LAZY)
    // @OrderBy("fecha_registro")
    private List<RegistroGlucemia> registros_glucemia = new ArrayList<>();

}
