package com.bus.app.DTO;

import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Conductor;
import com.bus.app.modelo.Ruta;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ViajeDTO {

    private Long id;
    private Date fecha;
    private Conductor conductor;
    private Ruta ruta;
    private Carro carro;
    private String comentarios;
    private String empresaServicioNombre;
    private String dadoAltaUser;
    private String deletedDriver;

    public ViajeDTO() {
    }

}
