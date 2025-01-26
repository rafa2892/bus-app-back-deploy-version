package com.bus.app.services;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.modelo.Viaje;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ViajeService {
    Viaje save(ViajeDTO viajeDTO);
    void delete(Viaje viaje);
    List<ViajeDTO> listAll();
    List<ViajeDTO> listByConductorId(@PathVariable Long id);
    ViajeDTO findViajeById(@PathVariable Long id);
    public Viaje convertToViaje(ViajeDTO viaje);
    public List<Viaje> filtrarViajes(String numeroUnidad, Long conductorId, String fechaDesde, String fechaHasta);




}
