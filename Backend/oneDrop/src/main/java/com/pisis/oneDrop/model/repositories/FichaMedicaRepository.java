package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.model.entities.FichaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FichaMedicaRepository extends JpaRepository<FichaMedica, Integer> {
}
