package com.bus.app.services;

import com.bus.app.DTO.CarroDTO;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;

import java.util.Optional;

public interface CarroService {
    Carro getCarro(CarroDTO carro);

    Historial save(Historial historial);

    Optional<Carro> findByid(Long id);

}
