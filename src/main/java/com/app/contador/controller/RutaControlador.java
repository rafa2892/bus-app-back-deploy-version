package com.app.contador.controller;

import com.app.contador.modelo.Ruta;
import com.app.contador.services.RutasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class RutaControlador {

    @Autowired
    private RutasServicio rutasSerivicio;

    @GetMapping("/rutas")
    public List<Ruta> listAll() {
        return rutasSerivicio.findAll();
    }


    @PostMapping("/rutas")
    public void guardarRuta(@RequestBody Ruta ruta) {
         rutasSerivicio.save(ruta);
    }

}
