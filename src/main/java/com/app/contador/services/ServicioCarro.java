package com.app.contador.services;

import com.app.contador.DTO.CarroDTO;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Historial;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface ServicioCarro {
    Carro getCarro(CarroDTO carro);

    Historial save(Historial historial);

    void parametrizarHistorial(Historial historial);

}
