package com.app.contador.DTO;

import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Conductor;
import com.app.contador.modelo.Ruta;
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
    private Integer kilometraje;
    private Integer horasEspera;
    private String comentarios;

    public ViajeDTO() {
    }

}
