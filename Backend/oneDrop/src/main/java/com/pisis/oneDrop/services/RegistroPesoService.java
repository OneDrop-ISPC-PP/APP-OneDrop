package com.pisis.oneDrop.services;

import com.pisis.oneDrop.exceptions.customsExceptions.NotFoundException;
import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.registros.RegistroAddDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroReadDto;
import com.pisis.oneDrop.model.dtos.registros.RegistroUpdateDto;
import com.pisis.oneDrop.model.entities.FichaMedica;
import com.pisis.oneDrop.model.entities.RegistroGlucemia;
import com.pisis.oneDrop.model.entities.RegistroPeso;
import com.pisis.oneDrop.model.mappers.RegistroPesoMapper;
import com.pisis.oneDrop.model.repositories.RegistroPesoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistroPesoService {
    @Autowired
    RegistroPesoRepository registroPesoRepository;
    @Autowired
    RegistroPesoMapper registroPesoMapper;

    @Autowired
    FichaMedicaService fichaMedicaService;

    // TODO validar datos de ficha medica
    public RegistroReadDto addRegistro (Integer id, RegistroAddDto registroAddDto){
        FichaMedica fichaMedicaDeUsuario = fichaMedicaService.getFichaMedicaById(id);
        // TODO validar datos de ficha medica
        // TODO validar datos de ficha medica
        RegistroPeso nuevoReg = registroPesoMapper.toEntity(registroAddDto);
        RegistroPeso regGuardado = registroPesoRepository.save(nuevoReg);
        fichaMedicaDeUsuario.getRegistros_peso().add(regGuardado);
        fichaMedicaService.fichaMedicaRepository.save(fichaMedicaDeUsuario);
        return registroPesoMapper.toReadDto(regGuardado);
    }


    public RegistrosPaginadosReadDtoArray findAllRegistrosByIdUser(Integer id, Integer page, Integer size , String sortBy ){

        Page<RegistroPeso> results;
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size /* , sort */);

        //todo PEDIENTEEEE
        //todo PEDIENTEEEE
        //todo PEDIENTEEEE


        results = registroPesoRepository.findAllRegistrosByIdUser(id, pageable);
        //todo PEDIENTEEEE
        //todo PEDIENTEEEE
        //todo PEDIENTEEEE

        Page pagedResults = results.map(entity -> registroPesoMapper.toReadDto(entity));
        return RegistrosPaginadosReadDtoArray.builder()
                .registros(pagedResults.getContent())
                .total_results(pagedResults.getTotalElements())
                .results_per_page(size)
                .current_page(page)
                .pages(pagedResults.getTotalPages())
                .sort_by("sortBy")
                .build();
    }

    public RegistroReadDto findById (Integer id){
        return registroPesoMapper.toReadDto(getRegistroById(id));
    }
    public RegistroPeso getRegistroById (Integer id){
        Optional<RegistroPeso> reg = registroPesoRepository.findById(id);
        if(reg.isEmpty()) throw new NotFoundException("Registro no encontrado por id: "+id);
        return reg.get();
    }

    public RegistroReadDto deleteRegistroById (Integer id){
        RegistroPeso regABorrar = getRegistroById(id);
        fichaMedicaService.deleteRegistroById(regABorrar);
        registroPesoRepository.deleteById(id);
        return registroPesoMapper.toReadDto(regABorrar);
    }

    // todo VALIDAR DATO DE ACTUALIZACION
    public RegistroReadDto editRegistro(Integer id, RegistroUpdateDto registroUpdateDto){
        Optional<RegistroPeso> registro = registroPesoRepository.findById(id);
        if (registro.isEmpty()){
            throw new NotFoundException("NO SE ENCONTRO REGISTRO CON EL ID "+id);
        }
        RegistroPeso regAEditar = registro.get();
        if(registroUpdateDto.getFecha() != null){
            regAEditar.setFecha(registroUpdateDto.getFecha());
        }
        if(registroUpdateDto.getValor() != null){
            regAEditar.setValor(registroUpdateDto.getValor());
        }
        if(registroUpdateDto.getComentario() != null){
            regAEditar.setComentario(registroUpdateDto.getComentario());
        }
        return registroPesoMapper.toReadDto(registroPesoRepository.save(regAEditar));
    }

}

