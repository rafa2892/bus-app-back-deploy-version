package com.app.contador.modelo;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="imagenes")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Lob // Anotación para campos grandes
    @Column(name = "imagen", columnDefinition = "MEDIUMBLOB")
    private byte[] imagen;

    @Column(name="imagen_desc")
    private String imagenDesc;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "carro_id")
    public Carro carro;


}
