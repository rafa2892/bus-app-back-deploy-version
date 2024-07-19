package com.app.contador.controller;
import com.app.contador.DTO.CarroDTO;
import com.app.contador.constantes.Constantes;
import com.app.contador.excepciones.ResourceNotFoundException;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Historial;
import com.app.contador.modelo.TipoVehiculo;
import com.app.contador.repositorio.CarrosRepositorio;
import com.app.contador.repositorio.ViajeRepositorio;
import com.app.contador.services.ServicioCarro;
import com.app.contador.services.ServicioTipoVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CarrosRepositorio carrosRepositorio;

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @Autowired
    private ServicioCarro servicioCarro;

    @Autowired
    private ServicioTipoVehiculo servicioTipoVehiculo;

    /*
    * Mapa que guarda los tipos de historiales
    * */
    private HashMap<Integer, String> map = new HashMap<>();

    @GetMapping("/carros")
    public List<Carro> listAll() {
     return carrosRepositorio.findAll();
    }

    @GetMapping("/carros/tipoVehiculos")
    public List<TipoVehiculo> listAllTipoVehiculos() {
        return servicioTipoVehiculo.findAll();
    }

    @PostMapping("/carros")
    public Carro guardarCarro(@RequestBody CarroDTO carroDTO) {
        Carro carro = servicioCarro.getCarro(carroDTO);
        Historial historial = new Historial();
        historial.setDescripcionTipo(Constantes.REGISTRO_VIAJE);
        historial.setId(Constantes.REGISTRO_VIAJE_ID);
        return carrosRepositorio.save(carro);

    }

    @PostMapping("/carros/agregarRegistro")
    public Carro agregarRegistro(@RequestBody CarroDTO carroDTO) {
        return null;
    }


    @GetMapping("/carros/{id}")
    public ResponseEntity<Carro> obtenerCarroPorid(@PathVariable Long id) {
        Carro carro = carrosRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carro no encontrado"));
        return ResponseEntity.ok(carro);
    }

    @PutMapping("/carros/{id}")
    public ResponseEntity<Carro> actualizarCarro(@PathVariable Long id , @RequestBody CarroDTO carroDTO) {
        Carro carro = servicioCarro.getCarro(carroDTO);
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
        Map<Long, String> tiposHistoriales = new HashMap<>(Constantes.getTiposHistoriales()); // Crear una copia del Map original
        tiposHistoriales.remove(1L);
        return  tiposHistoriales;

    }
    @PostMapping("/carros/guardarHistorial")
    public Historial registrarHistorial(@RequestBody Historial historial) {
        this.servicioCarro.parametrizarHistorial(historial);
        return this.servicioCarro.save(historial);
    }
}
