package com.app.contador.services;

import com.app.contador.modelo.Ruta;
import com.app.contador.repositorio.RutasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioRutasImpl implements ServicioRutas {

    @Autowired
    private RutasRepositorio rutaRepositorio;


    public List<Ruta> findAll() {
        return rutaRepositorio.findAll();
    }

}
