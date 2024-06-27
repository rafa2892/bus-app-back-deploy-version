package com.app.contador.controller;

import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Estado;
import com.app.contador.services.ServicioEstado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rutas/")
@CrossOrigin
public class EstadoControlador {

    @Autowired
    private ServicioEstado servicioEstado;

    @GetMapping("/estados")
    public List<Estado> listAll() {
        return servicioEstado.findAll();
    }

}
