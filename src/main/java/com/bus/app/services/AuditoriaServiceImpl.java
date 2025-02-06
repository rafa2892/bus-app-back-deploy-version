package com.bus.app.services;

import com.bus.app.modelo.RegistroActividad;
import com.bus.app.repositorio.AuditoriaRepositorio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    @Autowired
    AuditoriaRepositorio auditRepo;

        private static final Logger logger = LogManager.getLogger(AuditoriaServiceImpl.class.getName());

    @Override
    public RegistroActividad saveRegistro(RegistroActividad auditoria) {
        return auditRepo.save(auditoria);
    }
    @Override
    public List<RegistroActividad> getAllRegistrosAudit() {
        return auditRepo.findAll();
    }
}



