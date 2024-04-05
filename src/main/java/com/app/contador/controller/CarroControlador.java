package com.app.contador.controller;
import com.app.contador.DTO.CarroDTO;
import com.app.contador.DTO.ImagenDTO;
import com.app.contador.excepciones.ResourceNotFoundException;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Imagen;
import com.app.contador.repositorio.CarrosRepositorio;
import com.app.contador.repositorio.ViajeRepositorio;
import com.app.contador.services.ServicioCarro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class CarroControlador {

    @Autowired
    private CarrosRepositorio repositorio;

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @Autowired
    private ServicioCarro servicioCarro;

    @GetMapping("/carros")
    public List<Carro> listAll() {
     return repositorio.findAll();
    }

    @PostMapping("/carros")
    public Carro guardarCarro(@RequestBody CarroDTO carroDTO) {
        Carro carro = servicioCarro.getCarro(carroDTO);
        return repositorio.save(carro);
    }

    @GetMapping("/carros/{id}")
    public ResponseEntity<Carro> obtenerCarroPorid(@PathVariable Long id) {
        Carro carro = repositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carro no encontrado"));
        return ResponseEntity.ok(carro);
    }

    @PutMapping("/carros/{id}")
    public ResponseEntity<Carro> actualizarCarro(@PathVariable Long id , @RequestBody CarroDTO carroDTO) {
        Carro carro = servicioCarro.getCarro(carroDTO);
        carro.setId(id);
        repositorio.save(carro);
        return ResponseEntity.ok(carro);
    }

    @DeleteMapping("/carros/{id}")
    public void eliminarCarro(@PathVariable Long id) {
        Optional<Carro> optionalCarro = repositorio.findById(id);
        optionalCarro.ifPresent(carro -> repositorio.delete(carro));
    }
}
