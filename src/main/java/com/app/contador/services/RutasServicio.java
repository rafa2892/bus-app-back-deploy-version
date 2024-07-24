package com.app.contador.services;


import com.app.contador.modelo.Ruta;

import java.util.List;

public interface RutasServicio {
List<Ruta> findAll();
Ruta save(Ruta ruta);
}
