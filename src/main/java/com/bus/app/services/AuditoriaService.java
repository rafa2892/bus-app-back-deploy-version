package com.bus.app.services;


import com.bus.app.modelo.RegistroActividad;

import java.util.List;

public interface AuditoriaService {
    RegistroActividad saveRegistro(RegistroActividad auditoria);
    List<RegistroActividad> getAllRegistrosAudit();

}
