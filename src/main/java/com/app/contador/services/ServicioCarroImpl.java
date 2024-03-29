package com.app.contador.services;


import com.app.contador.DTO.CarroDTO;
import com.app.contador.DTO.ImagenDTO;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Imagen;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioCarroImpl implements ServicioCarro {


    @Override
    public Carro getCarro(CarroDTO carroDTO) {

        Carro carro = new Carro();
        List<ImagenDTO> imagenesDTO = carroDTO.getImagenes();

        carro.setModelo(carroDTO.getModelo());
        carro.setConsumo(carroDTO.getConsumo());
        carro.setAnyo(carroDTO.getAnyo());
        carro.setMarca(carroDTO.getMarca());
        carro.setNumeroUnidad(carroDTO.getNumeroUnidad());

        if(imagenesDTO != null && !imagenesDTO.isEmpty()) {
        List<Imagen> listaImagenes = imagenesDTO.stream()
                .map(imagenDTO -> {
                    Imagen imagen = new Imagen();
                    imagen.setImagenDesc(imagenDTO.getImagenDesc());
                    imagen.setImagen(imagenDTO.getImagenUrl().getBytes());
                    return imagen;
                }).toList();

        carro.setImagenes(listaImagenes);
        }


        List<Imagen> imagenes = carro.getImagenes();
        if (imagenes != null) {
            for (Imagen imagen : imagenes) {
                imagen.setCarro(carro);
            }
        }

        
        return carro;
    }
}