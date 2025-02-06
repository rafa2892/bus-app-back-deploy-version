package com.bus.app.repositorio;

import com.bus.app.modelo.RegistroActividad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditoriaRepositorio extends JpaRepository<RegistroActividad,Long> {
    List<RegistroActividad> findAllByOrderByFechaDesc();
}
