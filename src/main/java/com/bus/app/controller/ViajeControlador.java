package com.bus.app.controller;

import com.bus.app.DTO.CarroDTO;
import com.bus.app.DTO.ViajeDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Carro;
import com.bus.app.repositorio.CarrosRepositorio;
import com.bus.app.repositorio.ViajeRepositorio;
import com.bus.app.services.CarroService;
import com.bus.app.modelo.Historial;
import com.bus.app.modelo.Viaje;
import com.bus.app.services.RegistroHistorialService;
import com.bus.app.services.ViajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private ViajeService viajeService;

    private static final Logger logger = LoggerFactory.getLogger(ViajeControlador.class);

    @GetMapping
    public ResponseEntity<List<ViajeDTO>> listAll() {
        try {
            List<ViajeDTO> viajes = viajeService.listAll();
            if (viajes.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            logger.error("Ocurrió un error al listar los viajes: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("conductor/{id}")
    public ResponseEntity<List<ViajeDTO>> listByConductorId(@PathVariable Long id) {
        // Validates input data (id)
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest().body(null); // 400 Bad Request
            }
            List<ViajeDTO> viajes = viajeService.listByConductorId(id);
            if (viajes.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            logger.error("Ocurrió un error al listar los viajes por conductor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViajeDTO> findbyId(@PathVariable Long id) {
        try {
            //Validates input data (id)
            if (id <= 0) {
                return ResponseEntity.badRequest().body(null);
            }

            ViajeDTO viaje = viajeService.findViajeById(id);

            if (viaje == null) {
                return ResponseEntity.notFound().build();
            }
            // HTTP 200 OK
            return ResponseEntity.ok(viaje);
        } catch (Exception e) {
            logger.error("Error al obtener el viaje con ID " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Viaje> guardarViaje(@RequestBody ViajeDTO viajeDTO) {
        try {
            Viaje viajeGuardado =  viajeService.guardarViaje(viajeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(viajeGuardado);
        } catch (Exception e) {
            logger.error("Ocurrió un error al guardar el viaje: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
