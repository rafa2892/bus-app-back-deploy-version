package com.app.contador.services;

import com.app.contador.modelo.Usuario;
import com.app.contador.repositorio.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("usuarioServicio")
public class UsuarioServicioImpl implements ServicioUsuario{


    @Autowired
    UsuariosRepositorio usuariosRepositorio;


    @Override
    public Usuario getUsuarioById(Long id) {
        return usuariosRepositorio.getUsuarioById(id);
    }
}
