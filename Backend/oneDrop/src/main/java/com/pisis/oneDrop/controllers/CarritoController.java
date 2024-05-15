package com.pisis.oneDrop.controllers;

import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.carrito.CarritoReadDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioAddDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioReadDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioUpdateDto;
import com.pisis.oneDrop.services.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/carrito/", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("isAuthenticated()")
public class CarritoController {
    @Autowired
    CarritoService carritoService;

    /*
    @GetMapping()
    public ResponseEntity<RegistrosPaginadosReadDtoArray> findAll (@RequestParam(required = false, defaultValue = "0") Integer page,
                                                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                                                   @RequestParam(required = false, defaultValue = "paciente") String sortBy){
        return new ResponseEntity<>(carritoService.findAll(page, size, sortBy), HttpStatus.OK);
    }
     */
    // crear un carrito para un usuario, lo crea vacio y por unica vez!
    @PostMapping("{idUser}")
    public ResponseEntity<CarritoReadDto> addCarrito (@PathVariable Integer idUser){
        return new ResponseEntity<>(carritoService.createCarrito(idUser) , HttpStatus.CREATED);
    }
    @GetMapping("{id}")
    public ResponseEntity<CarritoReadDto> findById (@PathVariable Integer id){
        return new ResponseEntity<>(carritoService.findById(id) , HttpStatus.OK);
    }
    // agregar servicios
    @PostMapping("{idCarrito}/{idServicio}")
    public ResponseEntity<CarritoReadDto> addServicioACarrito (@PathVariable Integer idCarrito,
                                                               @PathVariable Integer idServicio){
        return new ResponseEntity<>(carritoService.addServicioACarrito(idCarrito,idServicio) , HttpStatus.ACCEPTED);
    }
    // quitar servicios
    @DeleteMapping("{idCarrito}/{idServicio}")
    public ResponseEntity<CarritoReadDto> removeServicioDeCarrito (@PathVariable Integer idCarrito,
                                                               @PathVariable Integer idServicio){
        return new ResponseEntity<>(carritoService.removeServicioDeCarrito(idCarrito,idServicio) , HttpStatus.ACCEPTED);
    }

    // todo faltan el resto de enpoindts, solo pueda agregar servicios, falta quitar servicios, falta "comprar" que es cuando se crean los resumens de compra ects..


    /*





    @DeleteMapping("{id}")
    public ResponseEntity<CarritoReadDto> deleteServicio (@PathVariable Integer id){
        return new ResponseEntity<>(carritoService.deleteById(id) , HttpStatus.ACCEPTED);
    }


     */





}
