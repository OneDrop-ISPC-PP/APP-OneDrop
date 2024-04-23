package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.model.entities.RegistroGlucemia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RegistroGlucemiaRepository extends JpaRepository<RegistroGlucemia , Integer> {
   // @Query("SELECT r FROM RegistroGlucemia r JOIN FichaMedica f WHERE f.paciente = :idUser")
    @Query("SELECT r FROM FichaMedica f JOIN f.registros_glucemia r WHERE f.paciente.id = :idUser")
    Page<RegistroGlucemia> findAllRegistrosByIdUser(Integer idUser, Pageable pageable);

}
