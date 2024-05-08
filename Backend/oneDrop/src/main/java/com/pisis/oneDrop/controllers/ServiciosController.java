package com.pisis.oneDrop.controllers;

import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaReadDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroGlucemiaUpdateDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioAddDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioReadDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioUpdateDto;
import com.pisis.oneDrop.services.RegistroGlucemiaService;
import com.pisis.oneDrop.services.ServicioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/servicios/", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()") // TODO  AND (hasRole('ADMIN') OR esDueñoDelRecurso() )
public class ServiciosController {

    @Autowired
    ServicioService servicioService;

    // todo DOCUMENTACION CON SWAGGER?

    //todo INFO NEGOCIO FALTANTE { SERVICIO ACTIVO??, DISPONIBILIDAD DE SERVICIO?, OWNERSHIP?, STOCK O CANT MAX POR TIEMPO? }
    @GetMapping()
    public ResponseEntity<RegistrosPaginadosReadDtoArray> findAllServicios (@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                            @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                            @RequestParam(required = false, defaultValue = "precio") String sortBy){
        return new ResponseEntity<>(servicioService.findAllServicios(page, size, sortBy), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ServicioReadDto> findServicioById (@PathVariable Integer id){
        return new ResponseEntity<>(servicioService.findById(id) , HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<ServicioReadDto> addNuevoServicio (@Valid @RequestBody ServicioAddDto servicioAddDto){
        return new ResponseEntity<>(servicioService.addRegistro(servicioAddDto) , HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<ServicioReadDto> editServicio (@PathVariable Integer id, @RequestBody ServicioUpdateDto servicioUpdateDto){
        return new ResponseEntity<>(servicioService.editServicio(id, servicioUpdateDto) , HttpStatus.ACCEPTED);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<ServicioReadDto> deleteServicio (@PathVariable Integer id){
        return new ResponseEntity<>(servicioService.deleteServicioById(id) , HttpStatus.ACCEPTED);
    }
}
