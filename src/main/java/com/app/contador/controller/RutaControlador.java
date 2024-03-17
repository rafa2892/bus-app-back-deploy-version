package com.app.contador.controller;

import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Ruta;
import com.app.contador.repositorio.RutasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class RutaControlador {

    @Autowired
    private RutasRepositorio rutasRepositorio;

    @GetMapping("/rutas")
    public List<Ruta> listAll() {
        return rutasRepositorio.findAll();
    }


}
