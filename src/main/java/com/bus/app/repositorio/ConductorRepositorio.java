package com.bus.app.repositorio;

import com.bus.app.modelo.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConductorRepositorio extends JpaRepository<Conductor,Long>, JpaSpecificationExecutor<Conductor> {
}

