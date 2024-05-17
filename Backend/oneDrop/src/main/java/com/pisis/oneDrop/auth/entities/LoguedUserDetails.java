package com.pisis.oneDrop.auth.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class LoguedUserDetails {
    String token;
    Integer id;
    String username;
    String dni;
    String email;
    Role role;
    String nombre;
    String apellido;
    String telefono;
    LocalDate nacimiento;
    String sexo;
    Collection<? extends GrantedAuthority> authorities;
}
