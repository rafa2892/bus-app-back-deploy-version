package com.bus.app.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name= "carros")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    private String marca;

    @Column(name = "anyo")
    private Long anyo;

    @Column(name = "consumo")
    private Long consumo;

    @Column(name = "numero_unidad", unique = true)
    private Long numeroUnidad;

    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;

    @Column(name = "ultimo_cambio_aceite")
    private Date ultimoCambioDeAceite;

    @Column(name = "siguiente_cambio_aceite")
    private Date siguienteCambioAceite;

    @OneToOne(mappedBy = "carro")
    private TituloPropiedad tituloPropiedad;

    @OneToOne(mappedBy = "carro")
    private PolizaUnidad poliza;

    @OneToOne(mappedBy = "carro")
    private Bateria bateria;

    @JsonManagedReference
    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Imagen> imagenes;

    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Historial> registroHistorial;















}