package com.pisis.oneDrop.controllers;

import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaReadDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroReadDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroUpdateDto;
import com.pisis.oneDrop.services.RegistroGlucemiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/registros/glucemia/", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()") // TODO  AND (hasRole('ADMIN') OR esDueñoDelRecurso() )
public class RegistroGlucemiaController {
    @Autowired
    RegistroGlucemiaService registroGlucemiaService;

    // todo DOCUMENTACION CON SWAGGER?
    @GetMapping("usuario/{id}")
    public ResponseEntity<RegistrosPaginadosReadDtoArray> findAllRegistrosByIdUser (@PathVariable Integer id,
                                                                          @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                          @RequestParam(required = false, defaultValue = "10") Integer size){
        return new ResponseEntity<>(registroGlucemiaService.findAllRegistrosByIdUser(id, page, size), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<RegistroReadDto> findRegistroGlucemia (@PathVariable Integer id){
        return new ResponseEntity<>(registroGlucemiaService.findById(id) , HttpStatus.OK);
    }
    @PostMapping("usuario/{id}")
    public ResponseEntity<RegistroReadDto> addRegistroGlucemia (@PathVariable Integer id, @Valid @RequestBody RegistroAddDto registroGlucemiaAddDto){
        return new ResponseEntity<>(registroGlucemiaService.addRegistro(id, registroGlucemiaAddDto) , HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<RegistroReadDto> editRegistroGlucemia (@PathVariable Integer id, @RequestBody RegistroUpdateDto registroGlucemiaUpdateDto){
        return new ResponseEntity<>(registroGlucemiaService.editRegistroGlucemia(id, registroGlucemiaUpdateDto) , HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<RegistroReadDto> deleteRegistroGlucemia (@PathVariable Integer id){
        return new ResponseEntity<>(registroGlucemiaService.deleteRegistroById(id) , HttpStatus.ACCEPTED);
    }
}
