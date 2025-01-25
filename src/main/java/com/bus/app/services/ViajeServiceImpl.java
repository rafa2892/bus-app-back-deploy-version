package com.bus.app.services;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;
import com.bus.app.modelo.Viaje;
import com.bus.app.repositorio.CarrosRepositorio;
import com.bus.app.repositorio.ViajeRepositorio;
import com.bus.app.security.BusAppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeServiceImpl implements ViajeService {

    @Autowired
    private ViajeRepositorio viajeRepositorio;

    @Autowired
    private CarrosRepositorio carroRepositorio;

    @Autowired
    private CarroService carroService;

    @Autowired
    private RegistroHistorialService registroHistorialService;

    @Override
    public List<ViajeDTO> listAll() {

        List<Viaje> listaViajes=  viajeRepositorio.findAll();
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listaViajes) {
            viajeDTOList.add(getViajeDTO(viaje));
        }
        return viajeDTOList;
    }

    @Override
    public Viaje save(ViajeDTO viajeDTO) {

        Viaje viaje = buildViaje(viajeDTO);



        Viaje viajeGuardado =  viajeRepositorio.save(viaje);

        if(viajeGuardado.getId() != null) {
            Historial historial = new Historial();
            historial.setIdTipo(Constantes.REGISTRO_VIAJE_ID);
            historial.setCarro(viaje.getCarro());

            if (viajeDTO.getComentarios() != null) {
                historial.setComentarios(viajeDTO.getComentarios());
            }else{
                historial.setComentarios(Constantes.REGISTRO_VIAJE);
            }
            this.registroHistorialService.parametrizarHistorial(historial);
            this.carroService.save(historial);
        }
        return viajeGuardado;
    }

    @Override
    public void delete(Viaje viaje) {
         viajeRepositorio.delete(viaje);
    }


    @Override
    public List<ViajeDTO> listByConductorId(Long id) {

        List<Viaje> listaViajes = viajeRepositorio.findByConductorId(id);
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje : listaViajes) {
            viajeDTOList.add(getViajeDTO(viaje));
        }
        return viajeDTOList;
    }
    @Override
    public ViajeDTO findViajeById(@PathVariable Long id) {
        Optional<Viaje> viaje = viajeRepositorio.findById(id);
        if(viaje.isEmpty()){
            return null;
        }else {
            Viaje viajeResult = viaje.get();
            return getViajeDTO(viajeResult);
        }
    }

    /**
     * Convierte una entidad {@link Viaje} en un objeto {@link ViajeDTO}.
     *
     * Este método es utilizado para transformar los datos de un {@link Viaje} que está en la base de datos
     * a un objeto {@link ViajeDTO} adecuado para ser transmitido a través de una API (por ejemplo, en una
     * respuesta JSON).
     *
     * @param viaje El objeto {@link Viaje} que se va a convertir en un {@link ViajeDTO}.
     * @return Un objeto {@link ViajeDTO} con los datos correspondientes del {@link Viaje}.
     */
    private ViajeDTO getViajeDTO(Viaje viaje) {

        ViajeDTO viajeDTO = new ViajeDTO();

        viajeDTO.setId(viaje.getId());
        viajeDTO.setRuta(viaje.getRuta());
        viajeDTO.setFecha(viaje.getFechaViaje());
        viajeDTO.setCarro(viaje.getCarro());
        viajeDTO.setConductor(viaje.getConductor());
        viajeDTO.setEmpresaServicioNombre(viaje.getEmpresaServicioNombre());
        viajeDTO.setDadoAltaUser(viaje.getDadoAltaUser());

        return viajeDTO;
    }

/**
 * Este método construye o crea una entidad `Viaje` a partir de un objeto `ViajeDTO`.
 * Se utiliza tanto para la creación de un nuevo viaje como para la edición de uno ya existente.
 *
 * @param viajeDTO El objeto DTO que contiene la información del viaje a construir.
 * @return El objeto `Viaje` correspondiente a los datos proporcionados por el DTO.
 */
   @Override
   public Viaje buildViaje(ViajeDTO viajeDTO) {

       //Comprobamos si es edición o creación
       Viaje viaje = null;
       Optional<Viaje> viajeEditar;

       if (viajeDTO.getId() != null && viajeDTO.getId() > 0) {
           viajeEditar = viajeRepositorio.findById(viajeDTO.getId());
            if (viajeEditar.isPresent()){
                viaje = viajeEditar.get();
            }
       } else {
           viaje = new Viaje();
           //Le seteamos el usuario que está creando el servicio
           String user = BusAppUtils.getUserName();
           viaje.setDadoAltaUser(user);
       }
       Optional<Carro> co = carroRepositorio.findById(viajeDTO.getCarro().getId());
       Carro carro = co.orElseGet(Carro::new);
       viaje.setCarro(carro);
       viaje.setRuta(viajeDTO.getRuta());
       viaje.setFechaViaje(new Date());
       viaje.setConductor(viajeDTO.getConductor());
       viaje.setEmpresaServicioNombre(viajeDTO.getEmpresaServicioNombre());


       return viaje;
   }

}

