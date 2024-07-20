package com.app.contador.services;

import com.app.contador.modelo.Usuario;
import com.app.contador.repositorio.UsuariosRepositorio;

public interface ServicioUsuario  {
    Usuario getUsuarioById(Long id);
}
