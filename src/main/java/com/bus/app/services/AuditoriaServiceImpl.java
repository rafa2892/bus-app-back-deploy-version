package com.bus.app.services;

import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.RegistroActividad;
import com.bus.app.modelo.UserLogin;
import com.bus.app.repositorio.AuditoriaRepositorio;
import com.bus.app.repositorio.UsuariosRepositorio;
import com.bus.app.tools.BusAppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    @Autowired
    AuditoriaRepositorio auditRepo;

    @Autowired
    UsuariosRepositorio usuRepo;

        private static final Logger logger = LogManager.getLogger(AuditoriaServiceImpl.class.getName());

    @Override
    public RegistroActividad saveRegistro(RegistroActividad auditoria) {
        return auditRepo.save(auditoria);
    }
    @Override
    public List<RegistroActividad> getAllRegistrosAudit() {
        return auditRepo.findAllByOrderByFechaDesc();
    }

    @Override
        public  void buildDeleteAudit(Object o) {
            String nombreEntidad = o.getClass().getSimpleName();

            String comentarioAudit = Constantes.MENSAJE_GENERICO_DELETE_AUDITORIA.concat(nombreEntidad);
            UserLogin user = usuRepo.findByUsu(BusAppUtils.getUserName());
            RegistroActividad audBorrado = new RegistroActividad(null,user.getUsu(),new Date(),comentarioAudit,3L,user.getRol());

            saveRegistro(audBorrado);
    }

    @Override
    public Page<RegistroActividad> listAllPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        return auditRepo.findAll(pageable);
    }

    @Override
    public Page<RegistroActividad> filtrarAuditBetweenDates(int page, int size, Date fechaDesde, Date fechaHasta) {

        Map<String, Date> fechasAjustadas = BusAppUtils.ajustarFechaDesdeHasta(fechaDesde, fechaHasta);

        fechaDesde = fechasAjustadas.get("fechaDesde");
        fechaHasta = fechasAjustadas.get("fechaHasta");

        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());

        // Filtrar por las fechas ajustadas
        return auditRepo.findByFechaBetween(fechaDesde, fechaHasta, pageable);
    }

}



