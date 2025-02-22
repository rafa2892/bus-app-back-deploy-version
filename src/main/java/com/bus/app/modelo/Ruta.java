package com.bus.app.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="rutas")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String origen;

    public String destino;
    public String distanciaKm;

    public String tiempoEstimado;

    public String ciudadOrigen;
    public String ciudadDestino;

    public String estadoOrigen;
    public String estadoDestino;












//    @ManyToOne
//    @JoinColumn(name = "estado_origen_id")
//    public Estado estadoOrigen;
//
//    @ManyToOne
//    @JoinColumn(name = "estado_destino_id")
//    public Estado estadoDestino;
}
