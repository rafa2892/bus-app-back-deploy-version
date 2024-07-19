package com.app.contador.repositorio;

import com.app.contador.modelo.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoVehiculoRepositorio  extends JpaRepository<TipoVehiculo,Long> {
}
