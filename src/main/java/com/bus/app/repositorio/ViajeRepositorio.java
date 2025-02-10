package com.bus.app.repositorio;


import com.bus.app.modelo.RegistroActividad;
import com.bus.app.modelo.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

public interface ViajeRepositorio extends JpaRepository<Viaje, Long>, JpaSpecificationExecutor<Viaje> {

    // Método para buscar viajes por el id del conductor
    List<Viaje> findByConductorId(Long conductorId);
    // Método para contar los viajes por el id del conductor
    long countByConductorId(Long conductorId);
    List<Viaje> findAllByOrderByFechaDesc();

    @Query("SELECT v FROM Viaje v WHERE DATE(v.fecha) = CURRENT_DATE")
    List<Viaje> findByFechaHoy();

    List<Viaje> findByFechaBetweenOrderByFechaDesc(Date fechaInicio, Date fechaFin);

    List<Viaje> findByFechaOrderByFechaDesc(Date fecha);

    List<Viaje> findByCarroIdOrderByFechaDesc(Long carroId);

    long countByCarroId(Long carroId);




}
