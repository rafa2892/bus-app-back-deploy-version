package com.bus.app.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarroListaDTO {

    private Long id;
    private String modelo;
    private String marca;
    private Long anyo;
    private Long consumo;
    private String tipoVehiculo;
    private Long numeroUnidad;
    private Date fechaAlta;
}
