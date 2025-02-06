package com.bus.app.controller;

import com.bus.app.DTO.HistorialDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.mappers.HistorialMapper;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;
import com.bus.app.services.CarroService;
import com.bus.app.services.HistorialService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @Autowired
    private CarroService carroService;

    private static final Logger logger = LogManager.getLogger(HistorialController.class.getName());

    @GetMapping("/historial")
    public ResponseEntity<List<Historial>> listAll() {
        List<Historial> historiales = historialService.findAll();

        if (historiales.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay historial
        }

        return ResponseEntity.ok(historiales); // Devuelve 200 OK con los datos
    }


    @PostMapping("/historial")
    public ResponseEntity<Historial> saveHistorial(@RequestBody HistorialDTO historialDTO) {

        try {
            Historial historial = HistorialMapper.toEntity(historialDTO);
            historialService.parametrizarHistorial(historial);
            Carro carroBD = carroService.findById(historial.getCarro().getId());
            historialService.save(historial);
            return ResponseEntity.status(HttpStatus.CREATED).body(historial);
        }catch(Exception e) {
                logger.error("Ocurrió un error contacte con el administrador: ", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

        @PutMapping("/historial/{id}")
        public ResponseEntity<Historial> actualizarCarro(@PathVariable Long id , @RequestBody HistorialDTO historialDTO)  {
            try {
                    return saveHistorial(historialDTO);
            }catch(Exception e) {
                logger.error("Ocurrió un error contacte con el administrador: ", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }


        @DeleteMapping("/historial/{id}")
        public ResponseEntity<Object> deleteHistorial(@PathVariable Long id) {
            try {
                historialService.delete(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }    catch(Exception e) {
                logger.error("Ocurrió un error contacte con el administrador: ", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }


    @GetMapping("/historial/{id}")
    public ResponseEntity<HistorialDTO> findById(@PathVariable Long id) {
        Optional<Historial> historial = historialService.findById(id);
        if (historial.isPresent()) {
            return historial.map(h -> ResponseEntity.ok(HistorialMapper.toDto(h)))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }


    @GetMapping("/historial/tiposRegistroHistorial")
    public ResponseEntity<Map<Long,String>> getTipoRegistroHistorial() {
        try {
            return ResponseEntity.ok(Constantes.getTiposHistoriales());
         }catch(Exception e) {
            logger.error("Ocurrió un error contacte con el administrador: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}