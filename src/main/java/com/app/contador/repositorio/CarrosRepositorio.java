package com.app.contador.repositorio;

import com.app.contador.modelo.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrosRepositorio extends JpaRepository<Carro,Long> {
}
