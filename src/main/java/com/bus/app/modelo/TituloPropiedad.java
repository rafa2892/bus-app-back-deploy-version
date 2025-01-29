package com.bus.app.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "titulo_unidad")
public class TituloPropiedad {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String descripcion;

    @Lob // Anotación para campos grandes
    @Column(name = "imagen", columnDefinition = "MEDIUMBLOB")
    private byte[] imagen;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id")
    public Carro carro;
}