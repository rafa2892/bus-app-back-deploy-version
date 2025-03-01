package com.bus.app.services;

import com.bus.app.modelo.Historial;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HistorialService {
   List<Historial> findAll();

   Historial save(Historial historial);

   void delete(Long id);

   Optional<Historial> findById(Long id);

   void parametrizarHistorial(Historial historial);

   Long countByCarroId(Long id);

   Page<Historial> findBycarBetweenDates(Long carroId, int page, int size,Date fechaDesde, Date fechaHasta);

   List<Historial> findByCarroId(Long id);

   Page<Historial> listByCarroIdPageable(Long id,int page,int size);

   boolean existsByViajeId(Long viajeId);

   void deleteHistorialByViajeId(Long viajeId);

   Optional<Historial> findByViajeId(Long viajeId);

}
