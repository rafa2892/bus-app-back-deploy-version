package com.bus.app.services;

import com.bus.app.DTO.CarroDTO;
import com.bus.app.DTO.CarroListaDTO;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Conductor;
import com.bus.app.modelo.Historial;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CarroService {
    Carro getCarro(CarroDTO carro);
    Page<Carro> findAll(int page, int size, String orderBy);
    Carro save(Carro carro);
    void delete(Long id);
    Carro findById(Long id);
    boolean existsByNumeroUnidad(Long numeroUnidad); // Devuelve true si ya existe
    Page<CarroListaDTO> listFilteredPageable(int page, int size, String marca, String modelo, Long anyo, Long numeroUnidad, String orderBy);
}
