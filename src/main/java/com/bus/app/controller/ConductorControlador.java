package com.bus.app.controller;
import com.bus.app.DTO.ViajeDTO;
import com.bus.app.modelo.Conductor;
import com.bus.app.modelo.Viaje;
import com.bus.app.repositorio.ViajeRepositorio;
import com.bus.app.services.ConductorService;
import com.bus.app.services.ViajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/conductores")
@CrossOrigin
public class ConductorControlador {

    @Autowired
    private ConductorService conductorService;

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @Autowired
    private ViajeService viajeServicio;

    private static final Logger logger = LoggerFactory.getLogger(ConductorControlador.class);

    @GetMapping
    public List<Conductor> listAll() {
        return conductorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conductor>findById(@PathVariable Long id){
        Optional<Conductor> conductor = conductorService.findById(id);
        try {
            if(conductor.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            else{
                Conductor conductorFound = conductor.get();
                return ResponseEntity.status(HttpStatus.OK).body(conductorFound);
            }
        }catch(Exception e) {
            logger.error("Ocurrió un error al listar los viajes: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Guarda un conductor nuevo o actualiza uno existente.
     * @param conductor El objeto de tipo {@link Conductor} a guardar.
     * @return La respuesta HTTP con el conductor guardado o un error si la operación falla.
     */
    @PostMapping()
    public ResponseEntity<Conductor>saveConductor(@RequestBody Conductor conductor) {
        try {
             Conductor conductorGuardado = conductorService.save(conductor);
            if(conductorGuardado.getId() == null || conductorGuardado.getId() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            else {
                return ResponseEntity.status(HttpStatus.CREATED).body(conductorGuardado);
            }
        }catch (Exception e) {
                logger.error("Ocurrió un error al listar los viajes: ", e);
                // En caso de error, devolvemos un mensaje de error con estado 400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
    }

    /**
     * Elimina un conductor por su ID.
     *
     * @param id El ID del conductor que se va a eliminar.
     * @return Respuesta HTTP con mensaje de éxito o error.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConductor(@PathVariable Long id) {
        try {
            List<ViajeDTO> listaViajePorConductor = viajeServicio.listByConductorId(id);
            if(listaViajePorConductor != null && !listaViajePorConductor.isEmpty()) {
                for(ViajeDTO i : listaViajePorConductor ) {
                    i.setDeletedDriver(i.getConductor().getNombre() + " " + i.getConductor().getApellido());
                    viajeServicio.save(i);
                }
            }
            // Intentamos eliminar al conductor por su ID
            conductorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Ocurrió un error al listar los viajes: ", e);
            // En caso de error, devolvemos un mensaje de error con estado 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar el conductor");
        }
    }
    /**
     * Actualiza la información de un conductor.
     * Este método maneja una solicitud HTTP PUT para actualizar los datos de un conductor. El conductor
     * se recibe en el cuerpo de la solicitud y se guarda en la base de datos. Si el conductor se guarda correctamente,
     * se retorna un estado 201 (Created) junto con el objeto del conductor actualizado. Si hay un problema en el proceso
     * de guardado, se retorna un estado 400 (Bad Request).
//     *
//     * @param  El objeto {@link Conductor} con los datos a actualizar.
//     * @return Respuesta HTTP con el conductor actualizado o error.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Conductor> updateConductor(@PathVariable Long id, @RequestBody Conductor conductor) {
        Conductor conductorActualizado = conductorService.save(conductor);
        if (conductorActualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(conductorActualizado);
        }
//     return ResponseEntity.ok().build();
    }
    /**
     * Contabiliza la cantidad de viajes asociados a un conductor específico.
     *
     * @param id El identificador del conductor cuyo número de viajes se desea contar.
     * @return Un valor de tipo Long que representa la cantidad de viajes registrados para el conductor con el ID proporcionado.
     */
    @GetMapping("/viaje-counter/{id}")
    public Long countByConductorId(@PathVariable Long id) {
        return viajeRepositorio.countByConductorId(id);
    }
}