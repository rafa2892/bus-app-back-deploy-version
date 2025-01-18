package com.bus.app.services;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;
import com.bus.app.modelo.Viaje;
import com.bus.app.repositorio.CarrosRepositorio;
import com.bus.app.repositorio.ViajeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeServiceImpl implements ViajeService {

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @Autowired
    private CarrosRepositorio carroRepositorio;

    @Autowired
    private CarroService carroService;

    @Autowired
    private RegistroHistorialService registroHistorialService;

    @Override
    public Viaje guardarViaje(ViajeDTO viajeDTO) {

        Viaje viaje = new Viaje();
        Carro carro = carroRepositorio.findById(viajeDTO.getCarro().getId()).get();

        viaje.setRuta(viajeDTO.getRuta());
        viaje.setCarro(carro);
        viaje.setFechaViaje(new Date());
        viaje.setConductor(viajeDTO.getConductor());

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

    @Override
    public List<ViajeDTO> listAll() {
        List<Viaje> listaViajes=  viajeRepositorio.findAll();
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listaViajes) {
            viajeDTOList.add(fillListViajeDto(viaje));
        }
        return viajeDTOList;
    }

    @Override
    public List<ViajeDTO> listByConductorId(Long id) {
        List<Viaje> listaViajes = viajeRepositorio.findByConductorId(id);
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje : listaViajes) {
            viajeDTOList.add(fillListViajeDto(viaje));
        }
        return viajeDTOList;
    }
    @Override
    public ViajeDTO findViajeById(@PathVariable Long id) {
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
        return viajeDTO;
    }

}

