package com.bus.app.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity()
@AllArgsConstructor
@NoArgsConstructor
@Table(name="registros_actividad")
public class RegistroActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private String userName;
    private Date fecha;
    private String comentarios;
    private Long tipoActividad;
    private String rol;

}
