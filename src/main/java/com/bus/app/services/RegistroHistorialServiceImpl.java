package com.bus.app.services;

import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Historial;
import com.bus.app.repositorio.RegistroHistorialRepositorio;
import com.bus.app.repositorio.UsuariosRepositorio;
import com.bus.app.security.BusAppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroHistorialServiceImpl implements RegistroHistorialService{

  @Autowired
  RegistroHistorialRepositorio registroHistorialRepositorio;

  @Autowired
  private UsuariosRepositorio usuariosRepositorio;


  @Override
  public List<Historial> findAll() {
    return registroHistorialRepositorio.findAll();
  }

  @Override
  public Optional<Historial> findById(Long id) {
    return registroHistorialRepositorio.findById(id);
  }

  @Override
  public Historial save(Historial historial) {
    return registroHistorialRepositorio.save(historial);
  }

  @Override
  public void parametrizarHistorial(Historial historial) {
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
}
