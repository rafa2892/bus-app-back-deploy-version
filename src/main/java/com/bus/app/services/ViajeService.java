package com.bus.app.services;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.modelo.Viaje;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ViajeService {

    Viaje save(ViajeDTO viajeDTO);

    void delete(Long id);

    List<ViajeDTO> listAll();

    List<ViajeDTO> listByConductorId(@PathVariable Long id);

    ViajeDTO findViajeById(@PathVariable Long id);

    Viaje convertToViaje(ViajeDTO viaje);

    List<Viaje> filtrarViajes(String numeroUnidad, Long conductorId, String fechaDesde, String fechaHasta);

    Page<ViajeDTO> filtrarViajesPaginados(String numeroUnidad, Long conductorId, String fechaDesde, String fechaHasta, int page, int size);

    List<ViajeDTO> listByCarroId(Long id);

    Page<ViajeDTO> listAllPageable(int page, int size);

    long countByCarroId(Long carroId);






}
