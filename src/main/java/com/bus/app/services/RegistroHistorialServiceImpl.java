package com.bus.app.services;

import com.bus.app.modelo.Historial;
import com.bus.app.repositorio.RegistroHistorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroHistorialServiceImpl implements RegistroHistorialService{

  @Autowired
  RegistroHistorialRepositorio registroHistorialRepositorio;


  @Override
  public List<Historial> findAll() {
    return registroHistorialRepositorio.findAll();
  }

  @Override
  public Optional<Historial> findById(Long id) {
    return registroHistorialRepositorio.findById(id);
  }
}
