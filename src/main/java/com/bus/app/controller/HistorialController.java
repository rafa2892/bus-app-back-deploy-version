package com.bus.app.controller;

import com.bus.app.excepciones.ResourceNotFoundException;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;
import com.bus.app.services.RegistroHistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class HistorialController {

    @Autowired
    private RegistroHistorialService registroHistorialService;

    @GetMapping("/historial")
    public List<Historial> listAll() {
        return registroHistorialService.findAll();
    }

    @GetMapping("/historial/{id}")
    public ResponseEntity<Historial> findById(@PathVariable Long id) {
        Optional<Historial> historial = registroHistorialService.findById(id);
        if(historial.isPresent()) {
            return ResponseEntity.ok(historial.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

}
