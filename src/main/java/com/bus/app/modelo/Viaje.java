package com.bus.app.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="viajes")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fechaViaje;

    @ManyToOne
    @JoinColumn(name = "carro_id")
    private Carro carro;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ruta_id")
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "empresa_servicio_ID")
    private Empresa empresaServicio;

    @Column(name = "nombre_empresa_servicio")
    private String empresaServicioNombre;

    @Column(name = "dado_alta_user")
    private String dadoAltaUser;
}
