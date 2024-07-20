package com.app.contador.repositorio;

import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Historial;
import com.app.contador.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepositorio extends JpaRepository<Usuario,Long> {
    Usuario getUsuarioById(Long id);
}
