package com.app.contador.DTO;

import com.app.contador.modelo.Imagen;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CarroDTO {

    private Long id;

    private String modelo;

    private String marca;

    private Long anyo;

    private Long consumo;

    private Long numeroUnidad;

    private List<ImagenDTO> imagenes;

    private String tipoDeVehiculo;

}
