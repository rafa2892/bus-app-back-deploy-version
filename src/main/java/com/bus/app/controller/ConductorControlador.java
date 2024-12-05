package com.bus.app.controller;
import com.bus.app.modelo.Conductor;
import com.bus.app.repositorio.ConductorRepositorio;
import com.bus.app.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class ConductorControlador {

    @Autowired
    private ConductorService conductorService;

    @GetMapping("/conductores")
    public List<Conductor> listAll() {
        return conductorService.findAll();
    }

    @GetMapping("/conductores/{id}")
    public ResponseEntity<Conductor>fingById(@PathVariable Long id){
        Optional<Conductor> conductor = conductorService.findById(id);
        if(conductor.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else{
            Conductor conductorFound = conductor.get();
            return ResponseEntity.status(HttpStatus.OK).body(conductorFound);
        }
    }

    /**
     * Guarda un conductor nuevo o actualiza uno existente.
     * @param conductor El objeto de tipo {@link Conductor} a guardar.
     * @return La respuesta HTTP con el conductor guardado o un error si la operación falla.
     */
    @PostMapping("/conductores")
    public ResponseEntity<Conductor>saveConductor(@RequestBody Conductor conductor) {

        Conductor conductorGuardado = conductorService.save(conductor);

        if(conductorGuardado.getId() == null || conductorGuardado.getId() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.CREATED).body(conductorGuardado);
        }
    }

    /**
     * Elimina un conductor por su ID.
     *
     * @param id El ID del conductor que se va a eliminar.
     * @return Respuesta HTTP con mensaje de éxito o error.
     */
    @DeleteMapping("/conductores/{id}")
    public ResponseEntity<String> deleteConductor(@PathVariable Long id) {
        try {
            // Intentamos eliminar al conductor por su ID
            conductorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // En caso de error, devolvemos un mensaje de error con estado 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar el conductor");
        }
    }

    /**
     * Actualiza la información de un conductor.
     *
     * Este método maneja una solicitud HTTP PUT para actualizar los datos de un conductor. El conductor
     * se recibe en el cuerpo de la solicitud y se guarda en la base de datos. Si el conductor se guarda correctamente,
     * se retorna un estado 201 (Created) junto con el objeto del conductor actualizado. Si hay un problema en el proceso
     * de guardado, se retorna un estado 400 (Bad Request).
     *
     * @param conductor El objeto {@link Conductor} con los datos a actualizar.
     * @return Respuesta HTTP con el conductor actualizado o error.
     */
    @PutMapping("/conductores/{id}")
    public ResponseEntity<Conductor> updateConductor(@RequestBody Conductor conductor) {
        // Guardamos el conductor actualizado
        Conductor conductorGuardado = conductorService.save(conductor);

        // Verificamos si el conductor se guardó correctamente
        if (conductorGuardado.getId() == null || conductorGuardado.getId() == 0) {
            // Si hubo un error en la creación o actualización, retornamos un error 400
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            // Si la actualización fue exitosa, retornamos el conductor con estado 201
            return ResponseEntity.status(HttpStatus.CREATED).body(conductorGuardado);
        }
    }


}