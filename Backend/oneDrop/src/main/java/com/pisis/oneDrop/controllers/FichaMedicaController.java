package com.pisis.oneDrop.controllers;

import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaAddDto;
import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaReadDto;
import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaUpdateDto;
import com.pisis.oneDrop.services.FichaMedicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/fichaMedica/", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class FichaMedicaController {
    @Autowired
    FichaMedicaService fichaMedicaService;

    // todo DOCUMENTACION CON SWAGGER?

    @GetMapping("{id}")
    public ResponseEntity<FichaMedicaReadDto> findFichaMedica (@PathVariable Integer id){
        return new ResponseEntity<>(fichaMedicaService.findById(id) , HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<FichaMedicaReadDto> addFichaMedica (@Valid @RequestBody FichaMedicaAddDto fichaMedicaAddDto){
        return new ResponseEntity<>(fichaMedicaService.addFichaMedica(fichaMedicaAddDto) , HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<FichaMedicaReadDto> editFichaMedica (@PathVariable Integer id, @RequestBody FichaMedicaUpdateDto fichaMedicaUpdateDto){
        return new ResponseEntity<>(fichaMedicaService.editFichaMedica(id, fichaMedicaUpdateDto) , HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<FichaMedicaReadDto> deleteFichaMedica (@PathVariable Integer id){
        return new ResponseEntity<>(fichaMedicaService.deleteFichaMedica(id) , HttpStatus.ACCEPTED);
    }

}
