package com.app.contador.repositorio;

import com.app.contador.modelo.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutasRespositorio extends JpaRepository<Ruta,Long> {
}
