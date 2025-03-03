package com.bus.app.services;

import com.bus.app.modelo.Conductor;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ConductorService

{
     Conductor save(Conductor conductor);
     Page<Conductor> findAll(int page, int size,String orderBy);
     void deleteById(Long id);
     Page<Conductor> obtenerConductoresConFiltro(String nombre, String apellido, String dni, int page, int size);
     Optional<Conductor> findById(Long id);
}
