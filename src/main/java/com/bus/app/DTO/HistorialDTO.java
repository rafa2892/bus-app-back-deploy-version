package com.bus.app.DTO;

import com.bus.app.modelo.Carro;
import com.bus.app.modelo.UserLogin;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    private UserLogin userLogin;
    private String dsHistorial;
}
