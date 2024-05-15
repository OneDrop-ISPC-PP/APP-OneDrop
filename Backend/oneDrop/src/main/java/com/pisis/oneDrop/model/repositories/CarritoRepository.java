package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.model.entities.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
}
