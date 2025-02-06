package com.bus.app.repositorio;

import com.bus.app.modelo.Historial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialRepositorio extends JpaRepository<Historial,Long> {
}
