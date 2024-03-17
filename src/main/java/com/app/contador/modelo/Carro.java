package com.app.contador.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name= "carros")
@Setter
@Getter
public class Carro {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    @Column(name = "anyo")
    private Long anyo;

    @Column(name = "consumo")
    private Long consumo;


    @Column(name = "numeroUnidad", unique = true)
    private Long numeroUnidad;

//    @OneToMany(mappedBy = "carro")
//    private List<Viaje> viajes;





}