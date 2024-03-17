package com.app.contador.repositorio;

import com.app.contador.modelo.Ruta;
import com.app.contador.modelo.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutasRepositorio  extends JpaRepository<Ruta,Long> {
}
