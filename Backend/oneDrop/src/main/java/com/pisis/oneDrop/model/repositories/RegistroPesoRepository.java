package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.model.entities.RegistroPeso;
import com.pisis.oneDrop.model.entities.RegistroTensionArterial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroPesoRepository extends JpaRepository<RegistroPeso, Integer> {
    @Query("SELECT r FROM FichaMedica f JOIN f.registros_peso r WHERE f.paciente.id = :idUser")
    Page<RegistroPeso> findAllRegistrosByIdUser(Integer idUser, Pageable pageable);
}
