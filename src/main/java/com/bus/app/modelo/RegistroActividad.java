package com.bus.app.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity()
@Table(name="registros_actividades")
public class RegistroActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String userName;
    private Date fecha;
    private String comentarios;
    private String tipoActividad;
    private String rol;

}
