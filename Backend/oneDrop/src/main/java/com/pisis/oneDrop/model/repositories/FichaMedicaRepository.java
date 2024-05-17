package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.auth.entities.User;
import com.pisis.oneDrop.model.entities.FichaMedica;
import com.pisis.oneDrop.model.entities.RegistroGlucemia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FichaMedicaRepository extends JpaRepository<FichaMedica, Integer> {

    Optional<FichaMedica> findByPaciente (User paciente);
    @Query("SELECT f FROM FichaMedica f JOIN f.registros_glucemia r WHERE r.id = :idRegistro")
    FichaMedica findFichaMedicaByIdRegistrosGlucemia (Integer idRegistro);
    @Query("SELECT f FROM FichaMedica f JOIN f.registros_tension_arterial r WHERE r.id = :idRegistro")
    FichaMedica findFichaMedicaByIdRegistrosTension (Integer idRegistro);
    @Query("SELECT f FROM FichaMedica f JOIN f.registros_peso r WHERE r.id = :idRegistro")
    FichaMedica findFichaMedicaByIdRegistrosPeso (Integer idRegistro);


}
