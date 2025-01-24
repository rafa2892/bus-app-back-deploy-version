package com.bus.app.repositorio;


import com.bus.app.modelo.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ViajeRepositorio extends JpaRepository<Viaje,Long> {

    // Método para buscar viajes por el id del conductor
    List<Viaje> findByConductorId(Long conductorId);

    // Método para contar los viajes por el id del conductor
    long countByConductorId(Long conductorId);

}
