package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.model.entities.RegistroGlucemia;
import com.pisis.oneDrop.model.entities.RegistroTensionArterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegistroTensionArterialRepository extends JpaRepository<RegistroTensionArterial, Integer> {
    @Query("SELECT r FROM FichaMedica f JOIN f.registros_tension_arterial r WHERE f.paciente.id = :idUser")
    Page<RegistroTensionArterial> findAllRegistrosByIdUser(Integer idUser, Pageable pageable);
}
