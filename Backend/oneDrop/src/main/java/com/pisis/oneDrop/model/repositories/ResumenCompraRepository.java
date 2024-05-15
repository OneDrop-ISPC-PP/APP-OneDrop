package com.pisis.oneDrop.model.repositories;

import com.pisis.oneDrop.model.entities.ResumenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumenCompraRepository extends JpaRepository<ResumenCompra, Integer> {
}
