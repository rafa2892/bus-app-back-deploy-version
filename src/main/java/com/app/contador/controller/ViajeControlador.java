package com.app.contador.controller;

import com.app.contador.DTO.ViajeDTO;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Conductor;
import com.app.contador.modelo.Viaje;
import com.app.contador.repositorio.CarrosRepositorio;
import com.app.contador.repositorio.ConductorRepositorio;
import com.app.contador.repositorio.ViajeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class ViajeControlador {

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @Autowired
    private CarrosRepositorio repositorio;

    @Autowired
    private ConductorRepositorio conductorRepositorio;


    @GetMapping("/viajes")
    public List<ViajeDTO> listAll() {

        List<Viaje> listaViajes=  viajeRepositorio.findAll();
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listaViajes) {
            viajeDTOList.add(new ViajeDTO(viaje.getId(),viaje.getPartida(),viaje.getDestino(),viaje.getFechaViaje(), viaje.getCarro().getModelo(), viaje.getConductor()));
        }

        return viajeDTOList;
    }

    @PostMapping("/viajes")
    public Viaje guardarViaje(@RequestBody ViajeDTO viajeDTO) {

        Viaje viaje = new Viaje();
        Carro carro = repositorio.findById(viajeDTO.getCarroId()).get();

        viaje.setPartida(viajeDTO.getOrigen());
        viaje.setDestino(viajeDTO.getDestino());
        viaje.setCarro(carro);
        viaje.setFechaViaje(new Date());
        viaje.setConductor(viajeDTO.getConductor());
        
        return viajeRepositorio.save(viaje);
    }

}
