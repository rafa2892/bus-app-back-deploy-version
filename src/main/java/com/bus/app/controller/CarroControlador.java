package com.bus.app.controller;

import com.bus.app.DTO.CarroListaDTO;
import com.bus.app.excepciones.ResourceNotFoundException;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Conductor;
import com.bus.app.modelo.TipoVehiculo;
import com.bus.app.modelo.TituloPropiedad;
import com.bus.app.repositorio.CarroRepositorio;
import com.bus.app.services.CarroService;
import com.bus.app.services.TipoVehiculoServicio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/carros")
@CrossOrigin
public class CarroControlador {

    private static final Logger logger = LogManager.getLogger(CarroControlador.class.getName());
    @Autowired
    private CarroRepositorio carrosRepositorio;
    @Autowired
    private CarroService carroService;
    @Autowired
    private TipoVehiculoServicio tipoVehiculoServicio;

    @GetMapping()
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
                        carro.getNumeroUnidad(),
                        carro.getFechaAlta()
                ))
                .toList();
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<CarroListaDTO>> listAllPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numeroUnidad-asc") String orderBy) {

        Page<Carro> carros = carroService.findAll(page,size,orderBy);
        // Mapear el contenido de la página (carros) a DTOs
        Page<CarroListaDTO> carroListaDTOs = carros.map(carro -> new CarroListaDTO(
                carro.getId(),
                carro.getModelo(),
                carro.getMarca(),
                carro.getAnyo(),
                carro.getConsumo(),
                carro.getTipoVehiculo(),
                carro.getNumeroUnidad(),
                carro.getFechaAlta()
        ));
        return ResponseEntity.ok(carroListaDTOs); // Return DTO list
    }


    @GetMapping("/filter-pageable")
    public  ResponseEntity<Page<CarroListaDTO>> listAllFilteredPageable(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Long anyo,
            @RequestParam(required = false) Long numeroUnidad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "numeroUnidad-asc") String orderBy) {
        try {
            Page<CarroListaDTO> carros =
                                    carroService.listFilteredPageable
                                                    (page, size, marca, modelo, anyo, numeroUnidad,orderBy);
            return ResponseEntity.ok(carros);
        } catch (Exception e) {
            logger.error("Ocurrió un error al filtrar los carros: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/tipoVehiculos")
    public List<TipoVehiculo> listAllTipoVehiculos() {
        return tipoVehiculoServicio.findAll();
    }

    @PostMapping()
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

    @PutMapping("/{id}")
    public ResponseEntity<Carro> actualizarCarro(@PathVariable Long id , @RequestBody Carro carro) {
        try {
            Carro carroGuardado  = carroService.save(carro);
            return ResponseEntity.ok().body(carroGuardado);
        }
        catch(Exception e) {
            logger.error("Ocurrió un error al actualizar el carro: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carro> findById(@PathVariable Long id) {
        try {
            Carro carro = carrosRepositorio.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carro no encontrado"));
            return ResponseEntity.ok(carro);
        } catch (Exception e) {
            logger.error("Ocurrió un error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCarro(@PathVariable Long id) {
        try {
            carroService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            logger.error("Ocurrió un error al borrar el vehiculo de la base datos: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/existe/{numeroUnidad}")
    public ResponseEntity<Boolean> existeCarro(@PathVariable Long numeroUnidad) {
        try {
            boolean existe = carroService.existsByNumeroUnidad(numeroUnidad);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            logger.error("Ocurrió un error: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/existeEdicion")
    public ResponseEntity<Boolean> verificarNumeroUnidadModoEdicion(
            @RequestParam("numeroUnidad") Long numeroUnidad,
            @RequestParam("carroId") Long carroId) {

            try{
                Carro carro = carroService.findById(carroId);

                if(carro.getNumeroUnidad().equals(numeroUnidad)) {
                    return ResponseEntity.ok(false);
                }else {
                   return existeCarro(numeroUnidad);
                }
            }
            catch(Exception e){
                logger.error("Ocurrió un error: ", e);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
    }

    @GetMapping("/existePDF/{id}")
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

    @GetMapping("/descargar/{id}")
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

    @PostMapping("/agregarRegistro")
    public Carro agregarRegistro(@RequestBody Carro carro) {
        return null;
    }
}
