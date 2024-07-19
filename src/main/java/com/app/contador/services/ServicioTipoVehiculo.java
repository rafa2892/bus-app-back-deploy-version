package com.app.contador.services;

import com.app.contador.DTO.CarroDTO;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.TipoVehiculo;

import java.util.List;

public interface ServicioTipoVehiculo {
    List<TipoVehiculo> findAll();

}
