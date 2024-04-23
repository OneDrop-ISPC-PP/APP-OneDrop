package com.pisis.oneDrop.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {
    String telefono;
    String nombre;
    String apellido;
    LocalDate nacimiento;
    String sexo;
}
