package com.bus.app.repositorio;

import com.bus.app.modelo.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuariosRepositorio extends JpaRepository<UserLogin,Long> {
    UserLogin findByid(Long id);

    UserLogin findByUsu(String usu);
}
