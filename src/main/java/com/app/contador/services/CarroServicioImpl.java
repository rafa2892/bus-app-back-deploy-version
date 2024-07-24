package com.app.contador.services;


import com.app.contador.DTO.CarroDTO;
import com.app.contador.DTO.ImagenDTO;
import com.app.contador.constantes.Constantes;
import com.app.contador.modelo.Carro;
import com.app.contador.modelo.Historial;
import com.app.contador.modelo.Imagen;
import com.app.contador.repositorio.CarrosRepositorio;
import com.app.contador.repositorio.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public void parametrizarHistorial(Historial historial) {
        historial.setDescripcionTipo(Constantes.getTiposHistoriales().get(historial.getIdTipo()));
        historial.setFechaAlta(new Date());

        //Obtenemos el Usuario por defecto
        historial.setUserLogin(usuariosRepositorio.getUsuarioById(1L));

        if (historial.getComentarios() == null ||
            historial.getComentarios().isEmpty() ||
            historial.getComentarios().isBlank()) {
                     historial.setComentarios("NO COMENTARIOS DISPONIBLE");
        }
    }
}