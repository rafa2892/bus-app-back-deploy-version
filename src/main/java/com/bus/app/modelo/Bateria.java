package com.bus.app.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "bateria")
public class Bateria {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String capacidad;
    private Date fechaInstalacion;
    private Date fechaRetiro;
    private String observaciones;

    @OneToOne
    @JoinColumn(name = "carro_id")
    @JsonBackReference
    public Carro carro;

}