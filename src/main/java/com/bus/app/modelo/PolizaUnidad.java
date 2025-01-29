package com.bus.app.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "poliza_unidad")
public class PolizaUnidad {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String aseguradora;
    private String poliza;
    private Boolean vigente;
    private String tipo;
    private String cobertura;
    private String observaciones;
    private Date fechaExpire;
    private Date fechaInicio;
    private Integer diasPorVencer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id")
    public Carro carro;
}