package com.pisis.oneDrop.auth;

import com.pisis.oneDrop.auth.dtos.UserReadDto;
import com.pisis.oneDrop.auth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserReadDto toReadDto(User u){
        return UserReadDto.builder()
                .id(u.getId())
                .username(u.getUsername())
                .dni(u.getDni())
                .telefono(u.getTelefono())
                .nombre(u.getNombre())
                .apellido(u.getApellido())
                .role(u.getRole())
                .email(u.getEmail())
                .sexo(u.getSexo())
                .nacimiento(u.getNacimiento())
                .build();
    }


}
