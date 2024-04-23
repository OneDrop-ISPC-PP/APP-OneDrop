package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.model.entities.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio , Integer> {
}
