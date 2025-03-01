package com.bus.app.controller;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.modelo.RegistroActividad;
import com.bus.app.services.AuditoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Controlador para gestionar las operaciones relacionadas con los registros de auditoría.
 */
//@Api(value = "Auditoría", tags = {"Registros de Actividad"})
@RestController
@RequestMapping("/api/v1/registros")
@CrossOrigin
public class RegistrosController {

    @Autowired
    AuditoriaService auditoriaService;

    private static final Logger logger = LoggerFactory.getLogger(RegistrosController.class);
    /**
     * Obtiene todos los registros de auditoría.
     *
     * @return ResponseEntity con la lista de registros de actividad o un mensaje de error en caso de no encontrar resultados.
     */
    // Endpoint que devuelve todos los registros de auditoría
    @GetMapping()
    public ResponseEntity<List<RegistroActividad>> getAllActivityAudits() {
        try {
            List<RegistroActividad> registros = auditoriaService.getAllRegistrosAudit();
            if (registros.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(registros);
        } catch (Exception e) {
            logger.error("Error al obtener listas de registros/auditoria");
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Obtiene una lista paginada de los registros de auditoría.
     *
     * Este método permite obtener los registros de auditoría en un formato paginado. El número de página y el tamaño
     * de la página son parámetros que se pasan en la solicitud. Si no se proporcionan, el número de página será 0
     * (la primera página) y el tamaño de la página será 20 por defecto.
     *
     * @param page El número de la página que se desea obtener (comienza desde 0).
     * @param size El número de registros por página.
     * @return ResponseEntity con una página de registros de auditoría o un mensaje de error en caso de fallar.
     */
    @GetMapping("/pageable")
    public ResponseEntity<Page<RegistroActividad>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<RegistroActividad> registrosAud = auditoriaService.listAllPageable(page, size);
            return ResponseEntity.ok(registrosAud);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/betweenDates")
    public ResponseEntity<Page<RegistroActividad>> getAuditsBetweenDates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin) {

        try {
            // No necesitamos convertir las fechas, solo verificamos si están presentes
            Page<RegistroActividad> registrosAud = auditoriaService.filtrarAuditBetweenDates(page, size, fechaInicio, fechaFin);

            if (registrosAud.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(registrosAud);
        } catch (Exception e) {
            logger.error("Error al obtener registros de auditoría entre fechas", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
