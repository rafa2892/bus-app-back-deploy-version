package com.bus.app.services;

import com.bus.app.modelo.Historial;

import java.util.List;
import java.util.Optional;

public interface RegistroHistorialService {
   List<Historial> findAll();

   Optional<Historial> findById(Long id);
}
