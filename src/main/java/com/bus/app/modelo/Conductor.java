package com.bus.app.modelo;


import com.bus.app.tools.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="conductores")
public class Conductor implements AuditableEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private String nombre;
    private String apellido;
    private String dni;
    private Date fechaNacimiento;
    private String dniTipo;
    private Date fechaAlta;
    private String dadoAltaPor;
    private Long kmRegistrados;


}
