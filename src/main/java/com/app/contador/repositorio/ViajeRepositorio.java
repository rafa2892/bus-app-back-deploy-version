package com.app.contador.repositorio;


import com.app.contador.modelo.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepositorio extends JpaRepository<Viaje,Long> {

}
