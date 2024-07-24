package com.app.contador.services;

import com.app.contador.modelo.Estado;
import com.app.contador.repositorio.EstadosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstadoServicioImpl implements EstadoServicio {

    @Autowired
    EstadosRepositorio estadosRepositorio;

    @Override
    public List<Estado> findAll() {
        return estadosRepositorio.findAll();
    }
}
