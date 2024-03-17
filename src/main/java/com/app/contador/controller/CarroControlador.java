package com.app.contador.controller;
import com.app.contador.excepciones.ResourceNotFoundException;
import com.app.contador.modelo.Carro;
import com.app.contador.repositorio.CarrosRepositorio;
import com.app.contador.repositorio.ViajeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class CarroControlador {

    @Autowired
    private CarrosRepositorio repositorio;

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @GetMapping("/carros")
    public List<Carro> listAll() {
        return repositorio.findAll();
    }

    @PostMapping("/carros")
    public Carro guardarCarro(@RequestBody Carro carro) {
        return repositorio.save(carro);
    }

    @GetMapping("/carros/{id}")
    public ResponseEntity<Carro> obtenerCarroPorid(@PathVariable Long id) {
        Carro carro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carro no encontrado"));
        return ResponseEntity.ok(carro);
    }

    @PutMapping("/carros/{id}")
    public ResponseEntity<Carro> actualizarEmpleado(@PathVariable Long id , @RequestBody Carro carroDetalles) {

        Carro carro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carro no encontrado"));
        carro.setModelo(carroDetalles.getModelo());
        carro.setAnyo(carroDetalles.getAnyo());
        carro.setConsumo(carroDetalles.getConsumo());
        return ResponseEntity.ok(carro);
    }

    @DeleteMapping("/carros/{id}")
    public void eliminarCarro(@PathVariable Long id) {
        Carro carro = (repositorio.findById(id).isPresent())  ? repositorio.findById(id).get() : null;
        repositorio.delete(carro);
    }
}
