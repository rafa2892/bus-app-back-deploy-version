package com.bus.app.DTO;

import com.bus.app.modelo.Carro;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HistorialDTO {

    private Long id;
    private Long idTipo;
    private String comentarios;
    private String descripcionTipo;
    private Carro carro;
    private Date fechaAlta;
    private String byUser;
    private String dsHistorial;
}
