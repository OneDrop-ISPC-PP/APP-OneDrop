package com.pisis.oneDrop.services;

import com.pisis.oneDrop.auth.AuthService;
import com.pisis.oneDrop.auth.UserMapper;
import com.pisis.oneDrop.auth.dtos.UserReadDto;
import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.exceptions.customsExceptions.ForbiddenAction;
import com.pisis.oneDrop.exceptions.customsExceptions.NotFoundException;
import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.carrito.CarritoReadDto;
import com.pisis.oneDrop.model.dtos.resumenCompra.ResumenCompraReadDto;
import com.pisis.oneDrop.model.entities.Carrito;
import com.pisis.oneDrop.model.entities.ResumenCompra;
import com.pisis.oneDrop.model.entities.Servicio;
import com.pisis.oneDrop.model.entities.enums.MetodoDePago;
import com.pisis.oneDrop.model.mappers.CarritoMapper;
import com.pisis.oneDrop.model.mappers.ResumenCompraMapper;
import com.pisis.oneDrop.model.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {
    @Autowired
    CarritoRepository carritoRepository;
    @Autowired
    CarritoMapper carritoMapper;
    @Autowired
    AuthService authService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ServicioService servicioService;
    @Autowired
    ResumenCompraService resumenCompraService;
    @Autowired
    ResumenCompraMapper resumenCompraMapper;

    public CarritoReadDto createCarrito(Integer idUser){
        User paciente = authService.getUserById(idUser);
        validarSiUsuarioYaTieneCarrito(paciente);
        Carrito newCarrito = new Carrito().builder()
                .paciente(paciente)
                .historialCompras(new ArrayList<>())
                .servicios(new ArrayList<>())
                .build();
        return carritoMapper.toReadDto(carritoRepository.save(newCarrito));
    }
    public void validarSiUsuarioYaTieneCarrito(User paciente){
        if(carritoRepository.findByPaciente(paciente).isPresent()) throw new ForbiddenAction("Paciente ya tiene un carrito asignado, no se puede crear otro.");
    }
    public Carrito getById (Integer id){
        Optional<Carrito> carrito = carritoRepository.findById(id);
        if(carrito.isEmpty()) throw new NotFoundException("No se encontro carrito por ID:" +id);
        return carrito.get();
    }
    public CarritoReadDto findById (Integer id){
        User paciente = authService.getUserById(id);
        Optional<Carrito> carrito = carritoRepository.findByPaciente(paciente);
        if(carrito.isEmpty()) throw new NotFoundException("No se encontro carrito por ID:" +id);
        return  carritoMapper.toReadDto(carrito.get());
    }

    public CarritoReadDto findByIdUser(Integer id){
        return  carritoMapper.toReadDto(getById(id));
    }
    public CarritoReadDto addServicioACarrito (Integer idCarrito, Integer idServicio){
        Servicio servicio = servicioService.getServicioById(idServicio);
        Carrito carrito = getById(idCarrito);
        // todo INICIALMENTE, VAMOS A SIMPLIFICAR LOGICA DE NEGOCIOS, PARA QUE NO EXISTA STOCK, SOLO SERVICIOS QUE LUEGO SE FIJARA FECHA PARA LA CITA, POR LO QUE NO HABRA STOCK NI CANTIDAD DE PRODUCTOS EN CARRITO
        Boolean existeServicioEnCarrito = carrito.getServicios().stream().anyMatch(s -> s.getId().equals(idServicio));
        if(existeServicioEnCarrito) throw new ForbiddenAction("Producto ya existe en carrito!");
        carrito.getServicios().add(servicio);
        return carritoMapper.toReadDto(carritoRepository.save(carrito));
    }
    public CarritoReadDto removeServicioDeCarrito (Integer idCarrito, Integer idServicio){
        Carrito carrito = getById(idCarrito);
        // todo INICIALMENTE, VAMOS A SIMPLIFICAR LOGICA DE NEGOCIOS, PARA QUE NO EXISTA STOCK, SOLO SERVICIOS QUE LUEGO SE FIJARA FECHA PARA LA CITA, POR LO QUE NO HABRA STOCK NI CANTIDAD DE PRODUCTOS EN CARRITO
        Boolean existeServicioEnCarrito = carrito.getServicios().stream().anyMatch(s -> s.getId().equals(idServicio));
        if( ! existeServicioEnCarrito ) throw new ForbiddenAction("Producto NO existe en carrito!");
        carrito.getServicios().removeIf(servicio -> servicio.getId().equals(idServicio));
        return carritoMapper.toReadDto(carritoRepository.save(carrito));
    }

    public ResumenCompraReadDto comprarCarrito(Integer idCarrito, MetodoDePago metodoDePago){
        Carrito carrito = getById(idCarrito);
        ResumenCompra resumenCompra = resumenCompraService.crearResumen(carrito, metodoDePago);
        carrito.getServicios().clear();
        carrito.getHistorialCompras().add(resumenCompra);
        carritoRepository.save(carrito);
        return resumenCompraMapper.toReadDto(resumenCompra);
    }

    /*

    public RegistrosPaginadosReadDtoArray findAll(Integer page, Integer size, String sortBy ){
        Page<Carrio> results;
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        results = servicioRepository.findAll(pageable);

        Page pagedResults = results.map(entity -> servicioMapper.toReadDto(entity));
        return RegistrosPaginadosReadDtoArray.builder()
                .registros(pagedResults.getContent())
                .total_results(pagedResults.getTotalElements())
                .results_per_page(size)
                .current_page(page)
                .pages(pagedResults.getTotalPages())
                .sort_by("sortBy")
                .build();
    }*/



}
