package com.bus.app.services;

import com.bus.app.modelo.Conductor;

import java.util.List;
import java.util.Optional;

public interface ConductorService

{
    public Conductor save(Conductor conductor);
    public List<Conductor> findAll();

    public void deleteById(Long id);

    Optional<Conductor> findById(Long id);
}
