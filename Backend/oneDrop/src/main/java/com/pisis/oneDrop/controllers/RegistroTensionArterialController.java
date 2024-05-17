package com.pisis.oneDrop.controllers;

import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.registros.*;
import com.pisis.oneDrop.services.RegistroGlucemiaService;
import com.pisis.oneDrop.services.RegistroPesoService;
import com.pisis.oneDrop.services.RegistroTensionArterialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/registros/tensionArterial/", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()") // TODO  AND (hasRole('ADMIN') OR esDueñoDelRecurso() )
public class RegistroTensionArterialController {
    @Autowired
    RegistroTensionArterialService registroTensionArterialService;

    // todo DOCUMENTACION CON SWAGGER?
    @GetMapping("usuario/{id}")
    public ResponseEntity<RegistrosPaginadosReadDtoArray> findAllRegistrosByIdUser (@PathVariable Integer id,
                                                                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                                    @RequestParam(required = false, defaultValue = "fecha_registro") String sortBy){
        return new ResponseEntity<>(registroTensionArterialService.findAllRegistrosByIdUser(id, page, size,sortBy), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<RegistroTensionReadDto> findRegistrobyId (@PathVariable Integer id){
        return new ResponseEntity<>(registroTensionArterialService.findById(id) , HttpStatus.OK);
    }
    @PostMapping("usuario/{id}")
    public ResponseEntity<RegistroTensionReadDto> addRegistro (@PathVariable Integer id, @Valid @RequestBody RegistroTensionAddDto registroAddDto){
        return new ResponseEntity<>(registroTensionArterialService.addRegistro(id, registroAddDto) , HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<RegistroTensionReadDto> editRegistro (@PathVariable Integer id, @RequestBody RegistroTensionUpdateDto registroUpdateDto){
        return new ResponseEntity<>(registroTensionArterialService.editRegistro(id, registroUpdateDto) , HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<RegistroTensionReadDto> deleteRegistro (@PathVariable Integer id){
        return new ResponseEntity<>(registroTensionArterialService.deleteRegistroById(id) , HttpStatus.ACCEPTED);
    }
}
