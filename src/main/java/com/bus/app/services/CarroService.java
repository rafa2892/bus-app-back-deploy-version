package com.bus.app.services;

import com.bus.app.DTO.CarroDTO;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;

import java.util.Optional;

public interface CarroService {
    Carro getCarro(CarroDTO carro);

    Carro save(Carro carro);

    void delete(Long id);
    Carro findById(Long id);

    boolean existsByNumeroUnidad(Long numeroUnidad); // Devuelve true si ya existe



}
