package com.bus.app.services;

import com.bus.app.modelo.Historial;

import java.util.List;
import java.util.Optional;

public interface HistorialService {
   List<Historial> findAll();
   Historial save(Historial historial);
   void delete(Long id);
   Optional<Historial> findById(Long id);
   void parametrizarHistorial(Historial historial);
}
