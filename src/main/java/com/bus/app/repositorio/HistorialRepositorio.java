package com.bus.app.repositorio;

import com.bus.app.modelo.Historial;
import com.bus.app.modelo.Viaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface HistorialRepositorio extends JpaRepository<Historial,Long> {

    Long countByCarroId(Long carroId);

    List<Historial> findByCarroIdAndFechaAltaBetweenOrderByFechaAltaDesc(Long carroId, Date fechaInicio, Date fechaFin);

    List<Historial> findByCarroIdOrderByFechaAltaDesc(Long carroId);

    Page<Historial> findByCarroId(Long carroId, Pageable pageable);
}
