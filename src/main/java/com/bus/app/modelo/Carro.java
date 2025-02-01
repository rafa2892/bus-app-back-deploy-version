package com.bus.app.modelo;

import com.bus.app.DTO.ImagenDTO;
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

    @OneToOne(cascade = CascadeType.ALL)
    private TituloPropiedad tituloPropiedad;

    @OneToOne(mappedBy = "carro", cascade = CascadeType.ALL)
    @JsonManagedReference
    private PolizaUnidad poliza;

    @OneToOne(mappedBy = "carro", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Bateria bateria;

    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Imagen> imagenesBd;

    @Transient
    private List<ImagenDTO> imagenes;  // Lista de imágenes (byte arrays)

    @OneToMany(mappedBy = "carro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Historial> registroHistorial;















}