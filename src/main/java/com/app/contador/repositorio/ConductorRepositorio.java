package com.app.contador.repositorio;

import com.app.contador.modelo.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConductorRepositorio extends JpaRepository<Conductor,Long> {
}
