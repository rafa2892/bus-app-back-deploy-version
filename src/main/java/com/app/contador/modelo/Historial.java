package com.app.contador.modelo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="historial")
public class Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_tipo")
    private Long idTipo;

    @Column(name = "descripcion")
    private String comentarios;

    @Column(name = "descripcion_tipo")
    private String descripcionTipo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "carro_id")
    private Carro carro;
}
