package com.bus.app.controller;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.modelo.Viaje;
import com.bus.app.services.ViajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/pageable")
    public ResponseEntity<Page<ViajeDTO>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<ViajeDTO> viajes = viajeService.listAllPageable(page, size);
            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
    public ResponseEntity<Viaje> save(@RequestBody ViajeDTO viajeDTO) {
        try {
            Viaje viajeGuardado = viajeService.save(viajeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(viajeGuardado);
        } catch (Exception e) {
            logger.error("Ocurrió un error al guardar el viaje: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viaje> update(@PathVariable Long id , @RequestBody ViajeDTO viajeDTO) {
        try {
            return save(viajeDTO);
        } catch (Exception e) {
            logger.error("Ocurrió un error al actualizar el viaje: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            viajeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Se ha borrado satisfactoriamente la entidad");
        } catch (Exception e) {
            logger.error("Ocurrió un error al guardar el viaje: ", e);
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

    @GetMapping("byCarro/{id}")
    public ResponseEntity<List<ViajeDTO>> listByCarroId(@PathVariable Long id) {
        // Validates input data (id)
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest().body(null); // 400 Bad Request
            }
            List<ViajeDTO> viajes = viajeService.listByCarroId(id);

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

    @GetMapping("countByCarro/{id}")
    public ResponseEntity<Long> countByCarroId(@PathVariable Long id) {
        // Validates input data (id)
        try {
            if (id <= 0) {
                return ResponseEntity.badRequest().body(null); // 400 Bad Request
            }
                Long c = viajeService.countByCarroId(id);
            return ResponseEntity.ok(c);
        } catch (Exception e) {
            logger.error("Ocurrió un error al listar los viajes por conductor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Backend: Controlador para filtrar viajes con paginación
    @GetMapping("/filtrar-paginado")
    public ResponseEntity<Page<ViajeDTO>> listViajesFiltrados(
            @RequestParam(required = false) String numeroUnidad,
            @RequestParam(required = false) Long conductorId,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            // Llamamos al servicio con los filtros y parámetros de paginación
            Page<ViajeDTO> viajes = viajeService.filtrarViajesPaginados(
                    numeroUnidad, conductorId, fechaDesde, fechaHasta, page, size);

            if (viajes == null || viajes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(viajes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    /* Método para filtrar los viajes sin paginación */
    @GetMapping("/filtrar")
    public  ResponseEntity<List<Viaje>> listViajesFiltrados(
            @RequestParam(required = false) String numeroUnidad,
            @RequestParam(required = false) Long conductorId,
            @RequestParam(required = false) String fechaDesde,
            @RequestParam(required = false) String fechaHasta) {

        try {
            // Llamamos al servicio para filtrar los viajes
            List<Viaje> viajes = viajeService.filtrarViajes(numeroUnidad, conductorId, fechaDesde, fechaHasta);
            if (viajes == null || viajes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(viajes);
        }catch (Exception e) {
            logger.error("Ocurrió un error al listar los viajes por conductor: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
