package com.bus.app.services;


import com.bus.app.DTO.CarroDTO;
import com.bus.app.DTO.ImagenDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;
import com.bus.app.modelo.Imagen;
import com.bus.app.repositorio.CarrosRepositorio;
import com.bus.app.repositorio.UsuariosRepositorio;
import com.bus.app.security.BusAppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarroServicioImpl implements CarroService {


    @Autowired
    private CarrosRepositorio carrosRepositorio;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Override
    public Carro getCarro(CarroDTO carroDTO) {

        Carro carro = new Carro();

        List<ImagenDTO> imagenesDTO = carroDTO.getImagenes();

        carro.setModelo(carroDTO.getModelo());
        carro.setConsumo(carroDTO.getConsumo());
        carro.setAnyo(carroDTO.getAnyo());
        carro.setMarca(carroDTO.getMarca());
        carro.setNumeroUnidad(carroDTO.getNumeroUnidad());
        carro.setTipoDeVehiculo(carroDTO.getTipoDeVehiculo());


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


    @Override
    public Historial save(Historial historial) {
        return this.carrosRepositorio.save(historial);
    }

    @Override
    public Optional<Carro> findByid(Long id) {
        return this.carrosRepositorio.findById(id);
    }
}