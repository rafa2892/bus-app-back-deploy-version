package com.bus.app.services;

import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Conductor;
import com.bus.app.repositorio.ConductorRepositorio;
import com.bus.app.tools.BusAppUtils;
import com.bus.app.tools.specification.ConductorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConductorServiceImpl implements ConductorService {


    @Autowired
    private ConductorRepositorio conductorRepositorio;

    @Autowired
    private AuditoriaService auditoriaService;

    /**
     * Guarda un conductor nuevo.
     * Al crear un conductor, asigna la fecha de alta en el formato DD/MM/YYYY HH:mm:ss
     *
     * @param conductor El conductor que se va a guardar
     * @return El conductor guardado
     */
    @Override
    public Conductor save(Conductor conductor) {
        // Asignamos la fecha de alta
        conductor.setFechaAlta(new Date());
        conductor.setDadoAltaPor(BusAppUtils.getUserName());

        // Guardamos el conductor
        return conductorRepositorio.save(conductor);
    }

    @Override
    public Page<Conductor> findAll(int page, int size, String orderBy) {
        Pageable pageable;
        if (orderBy != null && !orderBy.isEmpty()) {
            String[] parts = orderBy.split("-"); // Separar por "-"
            if (parts.length == 2) {
                String field = parts[0]; // Attribute to orderBy
                Sort.Direction direction = parts[1].equalsIgnoreCase("desc") ?
                        Sort.Direction.DESC : Sort.Direction.ASC;
                pageable = PageRequest.of(page, size, Sort.by(direction, field));
            } else {
                pageable = PageRequest.of(page, size); // Without order in case of no valid input
            }
        } else {
            pageable = PageRequest.of(page, size);// Without order in case of no valid input
        }
        return conductorRepositorio.findAll(pageable);
    }

//    @Override
//    public Page<Conductor> findAll(int page, int size,String orderBy) {
//
//
//
////        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaAlta").descending());
//        Pageable pageable = PageRequest.of(page, size);
//        return conductorRepositorio.findAll(pageable);
//    }

    @Override
    public Page<Conductor> obtenerConductoresConFiltro(String nombre, String apellido, String dni, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<Conductor> specification = ConductorSpecification.filtrarConductores(nombre, apellido, dni);

        return conductorRepositorio.findAll(specification, pageRequest);
    }


    @Override
    public void deleteById(Long id) {

        Conductor conductor = null;
        Optional<Conductor> co = conductorRepositorio.findById(id);

        if(co.isPresent()) {
            conductor = co.get();
            conductorRepositorio.delete(conductor);
        }
        //Creamos auditoria de eliminación
        auditoriaService.buildDeleteAudit(conductor);
        conductorRepositorio.deleteById(id);
    }

    @Override
    public Optional<Conductor> findById(Long id) {
       return conductorRepositorio.findById(id);
    }
}
