package com.pisis.oneDrop.services;

import com.pisis.oneDrop.exceptions.customsExceptions.NotFoundException;
import com.pisis.oneDrop.model.dtos.RegistrosPaginadosReadDtoArray;
import com.pisis.oneDrop.model.dtos.registros.*;
import com.pisis.oneDrop.model.entities.FichaMedica;
import com.pisis.oneDrop.model.entities.RegistroPeso;
import com.pisis.oneDrop.model.entities.RegistroTensionArterial;
import com.pisis.oneDrop.model.mappers.RegistroTensionArterialMapper;
import com.pisis.oneDrop.model.repositories.RegistroTensionArterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistroTensionArterialService {
    @Autowired
    RegistroTensionArterialRepository registroTensionArterialRepository;
    @Autowired
    RegistroTensionArterialMapper registroTensionArterialMapper;

    @Autowired
    FichaMedicaService fichaMedicaService;

    // TODO validar datos de ficha medica
    public RegistroTensionReadDto addRegistro (Integer id, RegistroTensionAddDto registroAddDto){
        FichaMedica fichaMedicaDeUsuario = fichaMedicaService.getFichaMedicaById(id);
        // TODO validar datos de ficha medica
        // TODO validar datos de ficha medica
        RegistroTensionArterial nuevoReg = registroTensionArterialMapper.toEntity(registroAddDto);
        RegistroTensionArterial regGuardado = registroTensionArterialRepository.save(nuevoReg);
        fichaMedicaDeUsuario.getRegistros_tension_arterial().add(regGuardado);
        fichaMedicaService.fichaMedicaRepository.save(fichaMedicaDeUsuario);
        return registroTensionArterialMapper.toReadDto(regGuardado);
    }


    public RegistrosPaginadosReadDtoArray findAllRegistrosByIdUser(Integer id, Integer page, Integer size , String sortBy ){

        Page<RegistroTensionArterial> results;
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size /* , sort */);

        //todo PEDIENTEEEE
        //todo PEDIENTEEEE
        //todo PEDIENTEEEE


        results = registroTensionArterialRepository.findAllRegistrosByIdUser(id, pageable);
        //todo PEDIENTEEEE
        //todo PEDIENTEEEE
        //todo PEDIENTEEEE

        Page pagedResults = results.map(entity -> registroTensionArterialMapper.toReadDto(entity));
        return RegistrosPaginadosReadDtoArray.builder()
                .registros(pagedResults.getContent())
                .total_results(pagedResults.getTotalElements())
                .results_per_page(size)
                .current_page(page)
                .pages(pagedResults.getTotalPages())
                .sort_by("sortBy")
                .build();
    }

    public RegistroTensionReadDto findById (Integer id){
        return registroTensionArterialMapper.toReadDto(getRegistroById(id));
    }
    public RegistroTensionArterial getRegistroById (Integer id){
        Optional<RegistroTensionArterial> reg = registroTensionArterialRepository.findById(id);
        if(reg.isEmpty()) throw new NotFoundException("Registro no encontrado por id: "+id);
        return reg.get();
    }

    public RegistroTensionReadDto deleteRegistroById (Integer id){
        RegistroTensionArterial regABorrar = getRegistroById(id);
        fichaMedicaService.deleteRegistroById(regABorrar);
        registroTensionArterialRepository.deleteById(id);
        return registroTensionArterialMapper.toReadDto(regABorrar);
    }

    // todo VALIDAR DATO DE ACTUALIZACION
    public RegistroTensionReadDto editRegistro(Integer id, RegistroTensionUpdateDto registroUpdateDto){
        Optional<RegistroTensionArterial> registro = registroTensionArterialRepository.findById(id);
        if (registro.isEmpty()){
            throw new NotFoundException("NO SE ENCONTRO REGISTRO CON EL ID "+id);
        }
        RegistroTensionArterial regAEditar = registro.get();
        if(registroUpdateDto.getFecha() != null){
            regAEditar.setFecha(registroUpdateDto.getFecha());
        }
        if(registroUpdateDto.getDiastolica() != null){
            regAEditar.setDiastolica(registroUpdateDto.getDiastolica());
        }
        if(registroUpdateDto.getSistolica() != null){
            regAEditar.setSistolica(registroUpdateDto.getSistolica());
        }
        if(registroUpdateDto.getComentario() != null){
            regAEditar.setComentario(registroUpdateDto.getComentario());
        }
        return registroTensionArterialMapper.toReadDto(registroTensionArterialRepository.save(regAEditar));
    }

}