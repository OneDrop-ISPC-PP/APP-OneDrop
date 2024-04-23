package com.pisis.oneDrop.services;

import com.pisis.oneDrop.auth.AuthService;
import com.pisis.oneDrop.auth.UserRepository;
import com.pisis.oneDrop.exceptions.customsExceptions.NotFoundException;
import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.servicios.ServicioAddDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioReadDto;
import com.pisis.oneDrop.model.dtos.servicios.ServicioUpdateDto;
import com.pisis.oneDrop.model.entities.Servicio;
import com.pisis.oneDrop.model.mappers.ServicioMapper;
import com.pisis.oneDrop.model.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioService {
    @Autowired
    ServicioRepository servicioRepository;

    @Autowired
    ServicioMapper servicioMapper;

    @Autowired
    AuthService authService;
    @Autowired
    private UserRepository userRepository;


    public RegistrosPaginadosReadDtoArray findAllServicios(Integer page, Integer size, String sortBy ){
        Page<Servicio> results;
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
    }

    // todo VALIDAR DATOS DE ACTUALIZACION
    public ServicioReadDto editServicio(Integer id, ServicioUpdateDto servicioUpdateDto){

        Optional<Servicio> servicio = servicioRepository.findById(id);
        if (servicio.isEmpty()){
            throw new NotFoundException("NO SE ENCONTRO SERVICIO CON EL ID "+id);
        }
        Servicio servicioAEditar = servicio.get();
        if(servicioUpdateDto.getNombre() != null){
            servicioAEditar.setNombre(servicioUpdateDto.getNombre());
        }
        if(servicioUpdateDto.getDescripcion() != null){
            servicioAEditar.setDescripcion(servicioUpdateDto.getDescripcion());
        }
        if(servicioUpdateDto.getPrecio() != null){
            servicioAEditar.setPrecio(servicioUpdateDto.getPrecio());
        }
        if(servicioUpdateDto.getComentarios() != null){
            servicioAEditar.setComentarios(servicioUpdateDto.getComentarios());
        }
        return servicioMapper.toReadDto(servicioRepository.save(servicioAEditar));
    }


    // TODO validar datos de SERVICIO
    public ServicioReadDto addRegistro(ServicioAddDto addDto){
        // TODO validar datos de SERVICIO
        // todo esta mal que servicio este en el aire, o bien depende del admin o depende de un medico.. revisar reglas negocio!
        Servicio nuevoServ = servicioMapper.toEntity(addDto);
        return servicioMapper.toReadDto(servicioRepository.save(nuevoServ));
    }
    public ServicioReadDto findById (Integer id){
        return servicioMapper.toReadDto(getServicioById(id));
    }
    public Servicio getServicioById (Integer id){
        Optional<Servicio> servicio = servicioRepository.findById(id);
        if(servicio.isEmpty()) throw new NotFoundException("Servicio no encontrado por id: "+id);
        return servicio.get();
    }

    public ServicioReadDto deleteServicioById (Integer id){
        Servicio servicioABorrar = getServicioById(id);
        servicioRepository.deleteById(id);
        return servicioMapper.toReadDto(servicioABorrar);
    }
}
