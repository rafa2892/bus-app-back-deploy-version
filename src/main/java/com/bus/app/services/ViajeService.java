package com.bus.app.services;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.modelo.Viaje;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ViajeService {
    Viaje guardarViaje(ViajeDTO viajeDTO);
    List<ViajeDTO> listAll();
    List<ViajeDTO> listByConductorId(@PathVariable Long id);
    ViajeDTO findViajeById(@PathVariable Long id);


}
