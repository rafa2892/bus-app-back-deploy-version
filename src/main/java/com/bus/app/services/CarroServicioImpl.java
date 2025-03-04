package com.bus.app.services;


import com.bus.app.DTO.CarroDTO;
import com.bus.app.DTO.CarroListaDTO;
import com.bus.app.DTO.ImagenDTO;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Imagen;
import com.bus.app.repositorio.CarroRepositorio;
import com.bus.app.tools.specification.CarroSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.bus.app.tools.BusAppUtils.buildPagination;

@Service
public class CarroServicioImpl implements CarroService {


    @Autowired
    private CarroRepositorio carroRepositorio;

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public Page<Carro> findAll(int page, int size, String orderBy) {
        Pageable pageable = buildPagination(page,size,orderBy);
        return carroRepositorio.findAll(pageable);
    }


    @Override
    public Carro save(Carro carro) {
        Carro carroGuardar = setCarroProperties(carro);
        return carroRepositorio.save(carroGuardar);
    }

    private Carro setCarroProperties(Carro carro) {

        if(carro.getImagenes() != null && carro.getImagenes().size() > 0) {
            getCarroWithFormattedImages(carro);
        }else {
            carro.setImagenesBd(new ArrayList<>());
        }

        if(carro.getBateria() !=  null ) {
            carro.getBateria().setCarro(carro);
        }

        if(carro.getPoliza() != null) {
            carro.getPoliza().setCarro(carro);
        }
        //Seteamos fecha de alta
        if(carro.getId() == null) {
            carro.setFechaAlta(new Date());
        }

        return carro;
    }

    @Override
    public Carro findById(Long id) {
        Optional<Carro> carroBD = carroRepositorio.findById(id);
        Carro carro = null;

        if(carroBD.isPresent()) {
            carro = carroBD.get();
        }

        return carro;
    }

    @Override
    public void delete(Long id) {
        Carro carro = null;
        Optional<Carro> co = carroRepositorio.findById(id);

        if(co.isPresent()) {
            carro = co.get();
            carroRepositorio.delete(carro);
        }
        //Creamos auditoria de eliminación
        auditoriaService.buildDeleteAudit(carro);
    }

    @Override
    public boolean existsByNumeroUnidad(Long numeroUnidad) {
        return carroRepositorio.existsByNumeroUnidad(numeroUnidad);
    }

    @Override
    public Page<CarroListaDTO> listFilteredPageable
            (int page, int size, String marca, String modelo, Long anyo, Long numeroUnidad, String orderBy) {

        Pageable pageable = buildPagination(page,size,orderBy);
        Specification<Carro> specification = CarroSpecification.filtrarCarros(marca, modelo, anyo, numeroUnidad);
        Page<Carro> c = carroRepositorio.findAll(specification, pageable);

        // Mapear el contenido de la página (carros) a DTOs
        return  c.map(carro -> new CarroListaDTO(
                carro.getId(),
                carro.getModelo(),
                carro.getMarca(),
                carro.getAnyo(),
                carro.getConsumo(),
                carro.getTipoVehiculo(),
                carro.getNumeroUnidad(),
                carro.getFechaAlta()
        ));
    }

    /**
     * Convierte un {@code CarroDTO} en un objeto {@code Carro} y procesa su lista de imágenes.
     * <p>
     * Este método realiza las siguientes acciones:
     * <ul>
     *     <li>Inicializa un nuevo objeto {@code Carro}.</li>
     *     <li>Asigna los valores principales del {@code CarroDTO} al objeto {@code Carro}.</li>
     *     <li>Procesa la lista de imágenes llamando a {@code getCarroWithFormattedImages}.</li>
     * </ul>
     *
     * @param carroDTO El objeto de transferencia de datos ({@code CarroDTO}) con la información del carro.
     * @return Un objeto {@code Carro} con la información asignada y sus imágenes procesadas.
     */
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


    /**
     * Procesa y actualiza la lista de imágenes de un {@code Carro} en base a los datos recibidos desde el frontend.
     * <p>
     * Este método realiza las siguientes acciones:
     * <ul>
     *     <li>Convierte la lista de {@code ImagenDTO} en una lista de entidades {@code Imagen}.</li>
     *     <li>Obtiene el estado actual del {@code Carro} desde la base de datos.</li>
     *     <li>Asigna la lista de imágenes persistidas al objeto {@code Carro} actual.</li>
     *     <li>Agrega nuevas imágenes que no tienen un ID (es decir, que no han sido persistidas).</li>
     *     <li>Elimina imágenes que ya no están presentes en la nueva lista.</li>
     * </ul>
     *
     * @param carro El objeto {@code Carro} que contiene la lista de imágenes a procesar.
     * @return El objeto {@code Carro} con la lista de imágenes actualizada.
     */
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

                Optional<Carro> o = carroRepositorio.findById(carro.getId());
                Carro cbd = new Carro();

                if(o.isPresent()) {cbd = o.get();}

                if(cbd.getImagenesBd() != null) {
                    carro.setImagenesBd(cbd.getImagenesBd());
                }else {
                    carro.setImagenesBd(new ArrayList<>());
                }

                //Agregamos imagenes no persistidas (nuevas imagenes)
                for(Imagen i : nuevasImagenes) {
                    if(i.id == null) {
                        carro.getImagenesBd().add(i);
                    }
                }

                //Ve si una imagen ha sido eliminada con el iterator para modificar la lista
                Iterator<Imagen> iterator = carro.getImagenesBd().iterator();
                while (iterator.hasNext()) {
                    Imagen imagenBd = iterator.next();
                    // Solo comparamos imágenes que tienen un id no nulo
                    if (imagenBd.getId() != null) {
                        // Verificar que nuevaImagen.getId() no sea null antes de comparar
                        boolean existsInNewList = nuevasImagenes.stream()
                                .anyMatch(nuevaImagen -> nuevaImagen.getId() != null && nuevaImagen.getId().equals(imagenBd.getId()));

                        // Si la imagen no está en nuevasImagenes, la eliminamos
                        if (!existsInNewList) {
                            iterator.remove();
                        }
                    }
                }
        }
        return carro;
    }
}

