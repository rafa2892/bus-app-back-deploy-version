package com.bus.app.DTO;

import com.bus.app.modelo.Bateria;
import com.bus.app.modelo.PolizaUnidad;
import com.bus.app.modelo.TituloPropiedad;
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

    private List<ImagenDTO> imagenesBD;

    private String tipoVehiculo;

    private TituloPropiedad tituloPropiedad;

    private PolizaUnidad poliza;

    private Bateria bateria;

}
