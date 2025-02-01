package com.bus.app.services;


import com.bus.app.DTO.CarroDTO;
import com.bus.app.DTO.ImagenDTO;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Imagen;
import com.bus.app.repositorio.CarroRepositorio;
import com.bus.app.repositorio.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarroServicioImpl implements CarroService {


    @Autowired
    private CarroRepositorio carrosRepositorio;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;


    @Override
    public Carro save(Carro carro) {

        if(carro.getImagenes() != null && carro.getImagenes().size() > 0) {
            getCarroWithFormattedImages(carro);
        }

        if(carro.getBateria() !=  null ) {
            carro.getBateria().setCarro(carro);
        }

        if(carro.getPoliza() != null) {
            carro.getPoliza().setCarro(carro);
        }

        return carrosRepositorio.save(carro);
    }

    @Override
    public Carro findById(Long id) {

        Optional<Carro> carroBD = carrosRepositorio.findById(id);
        Carro carro = null;

        if(carroBD.isPresent())
            carro = carroBD.get();

        return carro;
    }


    @Override
    public void delete(Long id) {
        Carro carro = null;

        Optional<Carro> co = carrosRepositorio.findById(id);
        if(co.isPresent()) {
            carro = co.get();
            carrosRepositorio.delete(carro);
        }
    }


    @Override
    public boolean existsByNumeroUnidad(Long numeroUnidad) {
        return carrosRepositorio.existsByNumeroUnidad(numeroUnidad);
    }


    @Override
    public Carro getCarro(CarroDTO carroDTO) {

        Carro carro = new Carro();

        List<ImagenDTO> imagenesDTO = carroDTO.getImagenesBD();

        carro.setModelo(carroDTO.getModelo());
        carro.setConsumo(carroDTO.getConsumo());
        carro.setAnyo(carroDTO.getAnyo());
        carro.setMarca(carroDTO.getMarca());
        carro.setNumeroUnidad(carroDTO.getNumeroUnidad());
        carro.setTipoVehiculo(carroDTO.getTipoVehiculo());

        return getCarroWithFormattedImages(carro);
    }


    private Carro getCarroWithFormattedImages(Carro carro) {

        List<ImagenDTO> imagenesDTO = carro.getImagenes();

        if (imagenesDTO != null && !imagenesDTO.isEmpty()) {

            List<Imagen> nuevasImagenes  = imagenesDTO.stream()

                .map(imagenDTO -> {
                    Imagen imagen = new Imagen();
                    imagen.setImagenDesc(imagenDTO.getImagenDesc());
                    imagen.setImagen(imagenDTO.getImagenUrl().getBytes());
                    imagen.setCarro(carro);
                    imagen.setId(imagenDTO.getId());
                    return imagen;
                }).toList();

                if(carro.getImagenesBd() == null) {
                    carro.setImagenesBd(new ArrayList<>());
                }

                for(Imagen i : nuevasImagenes) {
                    if(i.id == null) {
                        carro.getImagenesBd().add(i);
                    }
                }
        }
        return carro;
    }
}

