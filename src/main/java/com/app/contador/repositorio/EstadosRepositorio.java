package com.app.contador.repositorio;

import com.app.contador.modelo.Estado;
import com.app.contador.modelo.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface EstadosRepositorio extends JpaRepository<Estado,Long> {


}



