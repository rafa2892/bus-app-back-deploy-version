package com.app.contador.DTO;

import com.app.contador.modelo.Conductor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ViajeDTO {

    private Long id;
    private String origen;
    private String destino;

    private Date fecha;
    private String carroDescripcion;

    private Long carroId;

    private Conductor conductor;


    // Constructor
    public ViajeDTO(Long viajeId, String origen, String destino, Date fecha, String carroDescripcion, Conductor conductor) {
        this.id = viajeId;
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.carroDescripcion = carroDescripcion;
        this.conductor = conductor;
    }
}
