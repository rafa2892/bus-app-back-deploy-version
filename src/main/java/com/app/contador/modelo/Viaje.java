package com.app.contador.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="viajes")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private Date fechaViaje;

    @ManyToOne
    @JoinColumn(name = "carro_id")
    public Carro carro;

    @ManyToOne
    @JoinColumn(name = "conductor_id")
    public Conductor conductor;

    @ManyToOne
    @JoinColumn(name = "ruta_id")
    public Ruta ruta;

}
