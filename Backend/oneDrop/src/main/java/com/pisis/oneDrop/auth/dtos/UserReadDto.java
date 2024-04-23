package com.pisis.oneDrop.auth.dtos;

import com.pisis.oneDrop.auth.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadDto {
    Integer id;
    String username;
    String dni;
    String telefono;
    String nombre;
    String apellido;
    String email;
    LocalDate nacimiento;
    String sexo;
    Role role;
}
