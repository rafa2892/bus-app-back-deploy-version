package com.bus.app.repositorio;

import com.bus.app.modelo.Historial;
import com.bus.app.modelo.RegistroActividad;
import com.bus.app.modelo.Viaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HistorialRepositorio extends JpaRepository<Historial,Long> {

    Long countByCarroId(Long carroId);

    List<Historial> findByCarroIdAndFechaAltaBetweenOrderByFechaAltaDesc(Long carroId, Date fechaInicio, Date fechaFin);

    Page<Historial> findByCarroIdAndFechaAltaBetweenOrderByFechaAltaDesc(
            Long carroId, Date fechaDesde, Date fechaHasta, Pageable pageable);

    List<Historial> findByCarroIdOrderByFechaAltaDesc(Long carroId);

    Page<Historial> findByCarroId(Long carroId, Pageable pageable);

    boolean existsByViajeId(Long viajeId);

    void deleteByViajeId(Long viajeId); // Method to delete history by trip ID

    Optional<Historial> findByViajeId(Long viajeId);


}
