package com.bus.app.services;

import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Historial;
import com.bus.app.repositorio.HistorialRepositorio;
import com.bus.app.tools.BusAppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialServiceImpl implements HistorialService {

  @Autowired
  HistorialRepositorio historialRepositorio;

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
    // The entity is deleted, and activity auditing is performed
    auditoriaService.buildDeleteAudit(historialEliminado);
  }

  @Override
  public void  parametrizarHistorial(Historial historial) {

    historial.setDescripcionTipo(Constantes.getTiposHistoriales().get(historial.getIdTipo()));
    historial.setFechaAlta(new Date());

    String user = BusAppUtils.getUserName();
    historial.setByUser(user);

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

  @Override
  public Page<Historial> listByCarroIdPageable(Long id, int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("fechaAlta").descending());
    return historialRepositorio.findByCarroId(id, pageable);
  }

  @Override
  public boolean existsByViajeId(Long viajeId) {
    return historialRepositorio.existsByViajeId(viajeId);
  }

  @Override
  public void deleteHistorialByViajeId(Long viajeId) {
    historialRepositorio.deleteByViajeId(viajeId);
  }

  @Override
  public Optional<Historial> findByViajeId(Long viajeId) {
    return historialRepositorio.findByViajeId(viajeId);
  }
}
