package com.pisis.oneDrop.services;

import com.pisis.oneDrop.auth.AuthService;
import com.pisis.oneDrop.auth.UserRepository;
import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.exceptions.customsExceptions.NotFoundException;
import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaAddDto;
import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaReadDto;
import com.pisis.oneDrop.model.dtos.fichaMedica.FichaMedicaUpdateDto;
import com.pisis.oneDrop.model.entities.FichaMedica;
import com.pisis.oneDrop.model.mappers.FichaMedicaMapper;
import com.pisis.oneDrop.model.repositories.FichaMedicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FichaMedicaService {
    @Autowired
    FichaMedicaRepository fichaMedicaRepository;

    @Autowired
    FichaMedicaMapper fichaMedicaMapper;

    @Autowired
    AuthService authService;
    @Autowired
    private UserRepository userRepository;

    // TODO leer todos no, porque deberia buscar todos los pacientes y luego de cada paciente buscar la ficha si es que la quiero, mas perf


    // todo VALIDAR DATO DE ACTUALIZACION
    public FichaMedicaReadDto editFichaMedica(Integer id, FichaMedicaUpdateDto fichaMedicaUpdateDto){
        Optional <FichaMedica> ficha = fichaMedicaRepository.findById(id);
        if (ficha.isEmpty()){
            throw new NotFoundException("NO SE ENCONTRO FICHA MEDICA CON EL ID "+id);
        }
        FichaMedica fichaAEditar = ficha.get();
        if(fichaMedicaUpdateDto.getTipo_diabetes() != null){
            fichaAEditar.setTipo_diabetes(fichaMedicaUpdateDto.getTipo_diabetes());
        }
        if(fichaMedicaUpdateDto.getTerapia_insulina() != null){
            fichaAEditar.setTerapia_insulina(fichaMedicaUpdateDto.getTerapia_insulina());
        }
        if(fichaMedicaUpdateDto.getTerapia_pastillas() != null){
            fichaAEditar.setTerapia_pastillas(fichaMedicaUpdateDto.getTerapia_pastillas());
        }
        if(fichaMedicaUpdateDto.getTipo_glucometro() != null){
            fichaAEditar.setTipo_glucometro(fichaMedicaUpdateDto.getTipo_glucometro());
        }
        if(fichaMedicaUpdateDto.getTipo_sensor() != null){
            fichaAEditar.setTipo_sensor(fichaMedicaUpdateDto.getTipo_sensor());
        }
        if(fichaMedicaUpdateDto.getObjetivo_glucosa() != null){
            fichaAEditar.setObjetivo_glucosa(fichaMedicaUpdateDto.getObjetivo_glucosa());
        }
        if(fichaMedicaUpdateDto.getComorbilidades() != null){
            fichaAEditar.setComorbilidades(fichaMedicaUpdateDto.getComorbilidades());
        }
        return fichaMedicaMapper.toReadDto(fichaMedicaRepository.save(fichaAEditar));
    }


    // TODO validar datos de ficha medica
    public FichaMedicaReadDto addFichaMedica(FichaMedicaAddDto fichaMedicaAddDto){
        Optional<User> paciente = userRepository.findById(fichaMedicaAddDto.getId_paciente());
        if (paciente.isEmpty()){
            throw new NotFoundException("NO SE ENCONTRO PACIENTE CON EL ID "+fichaMedicaAddDto.getId_paciente());
        }
        FichaMedica nuevaFicha = fichaMedicaMapper.toEntity(fichaMedicaAddDto);
        nuevaFicha.setPaciente(paciente.get());
        return fichaMedicaMapper.toReadDto(fichaMedicaRepository.save(nuevaFicha));
    }
    public FichaMedicaReadDto findById (Integer id){
        return fichaMedicaMapper.toReadDto(getFichaMedicaById(id));
    }
    public FichaMedica getFichaMedicaById (Integer id){
        Optional<FichaMedica> ficha = fichaMedicaRepository.findById(id);
        if(ficha.isEmpty()) throw new NotFoundException("Ficha medica no encontrada por id: "+id);
        return ficha.get();
    }

    public FichaMedicaReadDto deleteFichaMedica (Integer id){
        FichaMedica fichaABorrar = getFichaMedicaById(id);
        fichaMedicaRepository.deleteById(id);
        return fichaMedicaMapper.toReadDto(fichaABorrar);
    }
}
