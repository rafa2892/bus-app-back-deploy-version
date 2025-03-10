package com.bus.app.repositorio;

import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Conductor;
import com.bus.app.modelo.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepositorio extends JpaRepository<Carro,Long>, JpaSpecificationExecutor<Carro> {
    Historial save(Historial historial);
    boolean existsByNumeroUnidad(Long numeroUnidad); // Devuelve true si ya existe

}
