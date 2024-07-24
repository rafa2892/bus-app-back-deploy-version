package com.app.contador.repositorio;

import com.app.contador.modelo.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepositorio extends JpaRepository<UserLogin,Long> {
    UserLogin getUsuarioById(Long id);

    UserLogin getUsuarioByUsu(String usu);
}
