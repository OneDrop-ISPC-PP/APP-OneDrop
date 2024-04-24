package com.pisis.oneDrop.controllers;

import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaReadDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaUpdateDto;
import com.pisis.oneDrop.services.RegistroGlucemiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registros/glucemia/")
@PreAuthorize("isAuthenticated()") // TODO  AND (hasRole('ADMIN') OR esDueñoDelRecurso() )
public class RegistroGlucemiaController {
    @Autowired
    RegistroGlucemiaService registroGlucemiaService;

    // todo DOCUMENTACION CON SWAGGER?
    @GetMapping("usuario/{id}")
    public ResponseEntity<RegistrosPaginadosReadDtoArray> findAllRegistrosByIdUser (@PathVariable Integer id,
                                                                          @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                          @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                          @RequestParam(required = false, defaultValue = "fecha_registro") String sortBy){
        return new ResponseEntity<>(registroGlucemiaService.findAllRegistrosByIdUser(id, page, size,sortBy), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<RegistroGlucemiaReadDto> findRegistroGlucemia (@PathVariable Integer id){
        return new ResponseEntity<>(registroGlucemiaService.findById(id) , HttpStatus.OK);
    }
    @PostMapping("usuario/{id}")
    public ResponseEntity<RegistroGlucemiaReadDto> addRegistroGlucemia (@PathVariable Integer id,@Valid @RequestBody RegistroGlucemiaAddDto registroGlucemiaAddDto){
        return new ResponseEntity<>(registroGlucemiaService.addRegistro(id, registroGlucemiaAddDto) , HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<RegistroGlucemiaReadDto> editRegistroGlucemia (@PathVariable Integer id, @RequestBody RegistroGlucemiaUpdateDto registroGlucemiaUpdateDto){
        return new ResponseEntity<>(registroGlucemiaService.editRegistroGlucemia(id, registroGlucemiaUpdateDto) , HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<RegistroGlucemiaReadDto> deleteRegistroGlucemia (@PathVariable Integer id){
        return new ResponseEntity<>(registroGlucemiaService.deleteRegistroById(id) , HttpStatus.ACCEPTED);
    }
}
