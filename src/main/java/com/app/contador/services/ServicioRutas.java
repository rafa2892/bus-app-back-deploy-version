package com.app.contador.services;


import com.app.contador.modelo.Ruta;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ServicioRutas {
List<Ruta> findAll();
}
