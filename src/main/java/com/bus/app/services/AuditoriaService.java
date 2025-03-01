package com.bus.app.services;


import com.bus.app.DTO.ViajeDTO;
import com.bus.app.modelo.RegistroActividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface AuditoriaService {

    RegistroActividad saveRegistro(RegistroActividad auditoria);
    List<RegistroActividad> getAllRegistrosAudit();
    void buildDeleteAudit(Object o);
    Page<RegistroActividad> listAllPageable(int page, int size);
    Page<RegistroActividad> filtrarAuditBetweenDates(int page, int size, Date fechaDesde, Date fechaHasta);

}
