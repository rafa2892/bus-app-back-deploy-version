package com.bus.app.controller;

import com.bus.app.DTO.CarroDTO;
import com.bus.app.DTO.CarroListaDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.excepciones.ResourceNotFoundException;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.TipoVehiculo;
import com.bus.app.repositorio.CarrosRepositorio;
import com.bus.app.services.CarroService;
import com.bus.app.services.TipoVehiculoServicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class CarroControlador {

    private static final Logger logger = LogManager.getLogger(CarroControlador.class.getName());
    @Autowired
    private CarrosRepositorio carrosRepositorio;
    @Autowired
    private CarroService carroService;
    @Autowired
    private TipoVehiculoServicio tipoVehiculoServicio;

    @GetMapping("/carros")
    public List<CarroListaDTO> listAll() {
        List<Carro> carros = carrosRepositorio.findAll();
        return carros.stream()
                .map(carro -> new CarroListaDTO(
                        carro.getId(),
                        carro.getModelo(),
                        carro.getMarca(),
                        carro.getAnyo(),
                        carro.getConsumo(),
                        carro.getTipoVehiculo(),
                        carro.getNumeroUnidad()
                ))
                .toList();
    }

    @GetMapping("/carros/tipoVehiculos")
    public List<TipoVehiculo> listAllTipoVehiculos() {
        return tipoVehiculoServicio.findAll();
    }

    @PostMapping("/carros")
    public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro) {

        try {
            Carro carroGuardado = carroService.save(carro);
            return ResponseEntity.status(HttpStatus.CREATED).body(carroGuardado);
        } catch (Exception e) {
            logger.error("Ocurrió un error al guardar el carro: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/carros/agregarRegistro")
    public Carro agregarRegistro(@RequestBody CarroDTO carroDTO) {
        return null;
    }

    @GetMapping("/carros/{id}")
    public ResponseEntity<Carro> findByid(@PathVariable Long id) {
        Carro carro = carrosRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carro no encontrado"));
        return ResponseEntity.ok(carro);
    }

    @PutMapping("/carros/{id}")
    public ResponseEntity<Carro> actualizarCarro(@PathVariable Long id , @RequestBody CarroDTO carroDTO) {
        Carro carro = carroService.getCarro(carroDTO);
        carro.setId(id);
        carrosRepositorio.save(carro);
        return ResponseEntity.ok(carro);
    }

    @DeleteMapping("/carros/{id}")
    public void eliminarCarro(@PathVariable Long id) {
        Optional<Carro> optionalCarro = carrosRepositorio.findById(id);
        optionalCarro.ifPresent(carro -> carrosRepositorio.delete(carro));
    }

    @GetMapping("/carros/tiposHistorial")
    public Map<Long,String> tipoHistoriales() {
        return new HashMap<>(Constantes.getTiposHistoriales());
    }
}
