package com.bus.app.controller;

import com.bus.app.DTO.CarroListaDTO;
import com.bus.app.excepciones.ResourceNotFoundException;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.TipoVehiculo;
import com.bus.app.modelo.TituloPropiedad;
import com.bus.app.repositorio.CarroRepositorio;
import com.bus.app.services.CarroService;
import com.bus.app.services.TipoVehiculoServicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class CarroControlador {

    private static final Logger logger = LogManager.getLogger(CarroControlador.class.getName());
    @Autowired
    private CarroRepositorio carrosRepositorio;
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

    @PutMapping("/carros/{id}")
    public ResponseEntity<Carro> actualizarCarro(@PathVariable Long id , @RequestBody Carro carro) {
        try {
            Carro carroBD  = carroService.save(carro);
            return ResponseEntity.ok(null);
        }
        catch(Exception e) {
            logger.error("Ocurrió un error al actualizar el carro: ", e);
            // En caso de error, devolvemos un mensaje de error con estado 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/carros/{id}")
    public ResponseEntity<Carro> findById(@PathVariable Long id) {
        try {
            Carro carro = carrosRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carro no encontrado"));
            return ResponseEntity.ok(carro);
        } catch (Exception e) {
            logger.error("Ocurrió un error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/carros/{id}")
    public ResponseEntity<Object> eliminarCarro(@PathVariable Long id) {
        try {
            carroService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            logger.error("Ocurrió un error al borrar el vehiculo de la base datos: ", e);
            // En caso de error, devolvemos un mensaje de error con estado 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/carros/existe/{numeroUnidad}")
    public ResponseEntity<Boolean> existeCarro(@PathVariable Long numeroUnidad) {
        try {
            boolean existe = carroService.existsByNumeroUnidad(numeroUnidad);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            logger.error("Ocurrió un error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("carros/existePDF/{id}")
    public ResponseEntity<Boolean> existePdfBBDD(@PathVariable Long id) {
        try {
            Carro carro = carroService.findById(id);

            boolean existePDF = carro.getTituloPropiedad() != null
                    && carro.getTituloPropiedad().getArchivoPDF() != null;

            return ResponseEntity.ok(existePDF);
        } catch (Exception e) {
            logger.error("Ocurrió un error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("carros/descargar/{id}")
    public ResponseEntity<byte[]> descargarArchivo(@PathVariable Long id) {
        try {

            Carro carro = carroService.findById(id);
            TituloPropiedad titulo = carro.getTituloPropiedad();

            // Configurar encabezados de la respuesta para descargar el archivo
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF) // Asegurar que se maneja como PDF
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + titulo.getArchivoPDFnombre() + "\"")
                    .body(titulo.getArchivoPDF());
        } catch (Exception e) {
            logger.error("Ocurrió un error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }




    @PostMapping("/carros/agregarRegistro")
    public Carro agregarRegistro(@RequestBody Carro carro) {
        return null;
    }
}
