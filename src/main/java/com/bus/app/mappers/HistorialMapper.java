package com.bus.app.mappers;

import com.bus.app.DTO.HistorialDTO;
import com.bus.app.modelo.Historial;

public class HistorialMapper {

    public static HistorialDTO toDto(Historial historial) {
        HistorialDTO dto = new HistorialDTO();
        dto.setDsHistorial(historial.getDsHistorial());
        dto.setCarro(historial.getCarro());
        dto.setComentarios(historial.getComentarios());
        dto.setDescripcionTipo(historial.getDescripcionTipo());
        dto.setFechaAlta(historial.getFechaAlta());
        dto.setId(historial.getId());
        dto.setIdTipo(historial.getIdTipo());
        dto.setUserLogin(historial.getUserLogin());
        return dto;
    }
    public static Historial toEntity(HistorialDTO dto) {
        Historial historial = new Historial();
        historial.setDsHistorial(dto.getDsHistorial());
        historial.setCarro(dto.getCarro());
        historial.setComentarios(dto.getComentarios());
        historial.setDescripcionTipo(dto.getDescripcionTipo());
        historial.setFechaAlta(dto.getFechaAlta());
        historial.setId(dto.getId());
        historial.setIdTipo(dto.getIdTipo());
        historial.setUserLogin(dto.getUserLogin());
        return historial;
    }

}
