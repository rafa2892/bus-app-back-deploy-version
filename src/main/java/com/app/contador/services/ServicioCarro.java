package com.app.contador.services;

import com.app.contador.DTO.CarroDTO;
import com.app.contador.modelo.Carro;
import org.springframework.stereotype.Service;

public interface ServicioCarro {
    Carro getCarro(CarroDTO carro);
}
