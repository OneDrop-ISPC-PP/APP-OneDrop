package com.pisis.oneDrop.model.entities.reports;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRegistroTensionArterialItem {
    Date fecha;
    Integer diastolica;
    Integer sistolica;
}