package com.bus.app.controller;

import com.bus.app.modelo.RegistroActividad;
import com.bus.app.services.AuditoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
