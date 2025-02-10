package com.bus.app.services;

import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Historial;
import com.bus.app.modelo.RegistroActividad;
import com.bus.app.modelo.UserLogin;
import com.bus.app.repositorio.HistorialRepositorio;
import com.bus.app.repositorio.UsuariosRepositorio;
import com.bus.app.tools.BusAppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialServiceImpl implements HistorialService {

  @Autowired
  HistorialRepositorio historialRepositorio;
  @Autowired
  private UsuariosRepositorio usuariosRepositorio;

  @Autowired
  private AuditoriaService auditoriaService;

  @Override
  public List<Historial> findAll() {
    return historialRepositorio.findAll();
  }

  @Override
  public Optional<Historial> findById(Long id) {
    return historialRepositorio.findById(id);
  }

  @Override
  public Historial save(Historial historial) {
    return historialRepositorio.save(historial);
  }

  @Override
  public void delete(Long id) {
    Optional<Historial> ho = historialRepositorio.findById(id);
    Historial historialEliminado = new Historial();

    if (ho.isPresent()) {
      historialEliminado = ho.get();
      historialRepositorio.deleteById(id);
    }
    // Se borra la entidad y realizamos auditoria de actividad
    auditoriaService.buildDeleteAudit(historialEliminado);
  }

  @Override
  public void  parametrizarHistorial(Historial historial) {

    historial.setDescripcionTipo(Constantes.getTiposHistoriales().get(historial.getIdTipo()));
    historial.setFechaAlta(new Date());

    String user = BusAppUtils.getUserName();
    historial.setUserLogin(usuariosRepositorio.findByUsu(user));

    if (historial.getComentarios() == null ||
          historial.getComentarios().isEmpty() ||
          historial.getComentarios().isBlank()) {
          historial.setComentarios("NO COMENTARIOS DISPONIBLE");
    }
  }

  @Override
  public Long countByCarroId(Long id) {
    return historialRepositorio.countByCarroId(id);
  }

  @Override
  public List<Historial> findBycarBetweenDates(Date fechaInicio, Date fechaFin, Long carroId) {
    fechaInicio = BusAppUtils.ajustarFechaDiaEspecifico(fechaInicio, fechaFin).get("fechaDesde");
    fechaFin = BusAppUtils.ajustarFechaDiaEspecifico(fechaInicio, fechaFin).get("fechaHasta");
    return
            historialRepositorio.findByCarroIdAndFechaAltaBetweenOrderByFechaAltaDesc(carroId, fechaInicio, fechaFin);
  }

  @Override
  public List<Historial> findByCarroId(Long id) {
    return historialRepositorio.findByCarroIdOrderByFechaAltaDesc(id);
  }
}
