package com.bus.app.controller;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Carro;
import com.bus.app.repositorio.CarrosRepositorio;
import com.bus.app.repositorio.ViajeRepositorio;
import com.bus.app.services.CarroService;
import com.bus.app.modelo.Historial;
import com.bus.app.modelo.Viaje;
import com.bus.app.services.RegistroHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/viajes")
@CrossOrigin
public class ViajeControlador {

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @Autowired
    private CarrosRepositorio repositorio;

    @Autowired
    private CarroService carroService;

    @Autowired
    private RegistroHistorialService registroHistorialService;


    @GetMapping
    public List<ViajeDTO> listAll() {

        List<Viaje> listaViajes=  viajeRepositorio.findAll();
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listaViajes) {
            viajeDTOList.add(fillListViajeDto(viaje));
        }
        return viajeDTOList;
    }

    @GetMapping("conductor/{id}")
    public ResponseEntity<List<ViajeDTO>> listByConductorId(@PathVariable Long id) {

        List<Viaje> listaViajes=  viajeRepositorio.findByConductorId(id);
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listaViajes) {
            viajeDTOList.add(fillListViajeDto(viaje));
        }

        if(viajeDTOList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(viajeDTOList);
    }

    @GetMapping("/{id}")
    public ViajeDTO findbyId(@PathVariable Long id) {

        Optional<Viaje> viaje = viajeRepositorio.findById(id);

        if(viaje.isEmpty()){
            return null;
        }
        else {
            Viaje viajeResult = viaje.get();
            return fillListViajeDto(viajeResult);
        }
    }


    private ViajeDTO fillListViajeDto(Viaje viaje) {
        ViajeDTO viajeDTO = new ViajeDTO();
        viajeDTO.setId(viaje.getId());
        viajeDTO.setRuta(viaje.getRuta());
        viajeDTO.setFecha(viaje.getFechaViaje());
        viajeDTO.setCarro(viaje.getCarro());
        viajeDTO.setConductor(viaje.getConductor());
        viajeDTO.setKilometraje(viaje.getKilometraje());
        viajeDTO.setHorasEspera(viaje.getHorasEspera());
        return viajeDTO;
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
            historial.setCarro(viaje.getCarro());

            if (viajeDTO.getComentarios() != null) {
             historial.setComentarios(viajeDTO.getComentarios());
            }
            else{
                historial.setComentarios(Constantes.REGISTRO_VIAJE);
            }

             this.registroHistorialService.parametrizarHistorial(historial);
             this.carroService.save(historial);
        }

        return viajeGuardado;
    }
}
