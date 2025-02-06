package com.bus.app.services;


import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.RegistroActividad;
import com.bus.app.modelo.UserLogin;
import com.bus.app.tools.BusAppUtils;

import java.util.Date;
import java.util.List;

public interface AuditoriaService {
    RegistroActividad saveRegistro(RegistroActividad auditoria);
    List<RegistroActividad> getAllRegistrosAudit();
    void buildDeleteAudit(Object o);


}
