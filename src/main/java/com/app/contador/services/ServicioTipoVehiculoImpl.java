package com.app.contador.services;


import com.app.contador.modelo.TipoVehiculo;
import com.app.contador.repositorio.TipoVehiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioTipoVehiculoImpl implements ServicioTipoVehiculo{

    @Autowired
    TipoVehiculoRepositorio tipoVehiculoRepositorio;

    @Override
    public List<TipoVehiculo> findAll() {
        return tipoVehiculoRepositorio.findAll();
    }
}
