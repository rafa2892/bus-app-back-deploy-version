package com.bus.app.services;


import com.bus.app.modelo.RegistroActividad;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AuditoriaService {

    RegistroActividad saveRegistro(RegistroActividad auditoria);

    List<RegistroActividad> getAllRegistrosAudit();

    void buildDeleteAudit(Object o);

    Page<RegistroActividad> listAllPageable(int page, int size);


}
