package com.app.contador.services;

import com.app.contador.DTO.CarroDTO;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Historial;

public interface CarroService {
    Carro getCarro(CarroDTO carro);

    Historial save(Historial historial);

    void parametrizarHistorial(Historial historial);

}
