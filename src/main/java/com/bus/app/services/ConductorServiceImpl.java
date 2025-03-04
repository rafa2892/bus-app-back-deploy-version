package com.bus.app.services;

import com.bus.app.modelo.Conductor;
import com.bus.app.repositorio.ConductorRepositorio;
import com.bus.app.tools.BusAppUtils;
import com.bus.app.tools.specification.ConductorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.bus.app.tools.BusAppUtils.buildPagination;

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
        Pageable pageable = buildPagination(page,size,orderBy);
        return conductorRepositorio.findAll(pageable);
    }

    @Override
    public Page<Conductor> obtenerConductoresConFiltro(String nombre, String apellido, String dni, int page, int size, String orderBy) {
        Pageable pageable =  buildPagination(page,size,orderBy);
        Specification<Conductor> specification = ConductorSpecification.filtrarConductores(nombre, apellido, dni);

        return conductorRepositorio.findAll(specification, pageable);
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
