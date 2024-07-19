package com.app.contador.controller;

import com.app.contador.DTO.ViajeDTO;
import com.app.contador.constantes.Constantes;
import com.app.contador.modelo.*;
import com.app.contador.repositorio.CarrosRepositorio;
import com.app.contador.repositorio.ConductorRepositorio;
import com.app.contador.repositorio.ViajeRepositorio;
import com.app.contador.services.ServicioCarro;
import com.app.contador.services.ServicioEstado;
import com.app.contador.services.ServicioRutas;
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
    private ServicioCarro servicioCarro;


    @GetMapping("/viajes")
    public List<ViajeDTO> listAll() {

        List<Viaje> listaViajes=  viajeRepositorio.findAll();
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listaViajes) {
            fillListViajeDto(viaje, viajeDTOList);
        }

        return viajeDTOList;
    }

    private void fillListViajeDto(Viaje viaje, List<ViajeDTO> viajeDTOList) {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(viaje.getId());
        viajeDTO.setRuta(viaje.getRuta());
        viajeDTO.setFecha(viaje.getFechaViaje());
        viajeDTO.setCarro(viaje.getCarro());
        viajeDTO.setConductor(viaje.getConductor());
        viajeDTO.setKilometraje(viaje.getKilometraje());
        viajeDTO.setHorasEspera(viaje.getHorasEspera());
        viajeDTOList.add(viajeDTO);
    }

    @PostMapping("/viajes")
    public Viaje guardarViaje(@RequestBody ViajeDTO viajeDTO) {

        Viaje viaje = new Viaje();
        Carro carro = repositorio.findById(viajeDTO.getCarro().getId()).get();

        viaje.setRuta(viajeDTO.getRuta());
        viaje.setCarro(carro);
        viaje.setFechaViaje(new Date());
        viaje.setConductor(viajeDTO.getConductor());
        viaje.setKilometraje(viajeDTO.getKilometraje());

        Viaje viajeGuardado =  viajeRepositorio.save(viaje);

        if(viajeGuardado.getId() != null) {
            Historial historial = new Historial();
            historial.setIdTipo(Constantes.REGISTRO_VIAJE_ID);
            historial.setComentarios(Constantes.REGISTRO_VIAJE);
            historial.setCarro(viaje.getCarro());
            this.servicioCarro.parametrizarHistorial(historial);
            this.servicioCarro.save(historial);
        }

        return viajeGuardado;
    }
}
