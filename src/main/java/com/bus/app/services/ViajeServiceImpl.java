package com.bus.app.services;

import com.bus.app.DTO.ViajeDTO;
import com.bus.app.constantes.Constantes;
import com.bus.app.modelo.Carro;
import com.bus.app.modelo.Historial;
import com.bus.app.modelo.Viaje;
import com.bus.app.repositorio.CarroRepositorio;
import com.bus.app.repositorio.ViajeRepositorio;
import com.bus.app.tools.BusAppUtils;
import com.bus.app.tools.specification.ViajeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private CarroRepositorio carroRepositorio;
    @Autowired
    private HistorialService historialService;
    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public List<ViajeDTO> listAll() {

        List<Viaje> listaViajes=  viajeRepositorio.findAllByOrderByFechaDesc();
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listaViajes) {
            viajeDTOList.add(convertToViajeDTO(viaje));
        }

        return viajeDTOList;
    }

    @Override
    public Viaje save(ViajeDTO viajeDTO) {

        Viaje viaje = convertToViaje(viajeDTO);
        Viaje viajeGuardado =  viajeRepositorio.save(viaje);

        if(viajeGuardado.getId() != null) {

            Historial historial = new Historial();
            historial.setIdTipo(Constantes.REGISTRO_VIAJE_ID);
            historial.setCarro(viaje.getCarro());
            historial.setViajeId(viaje.getId());

            if (viajeDTO.getComentarios() != null) {
                historial.setComentarios(viajeDTO.getComentarios());
            }else{
                historial.setComentarios(Constantes.REGISTRO_VIAJE);
            }
            historialService.parametrizarHistorial(historial);
            historialService.save(historial);
        }
        return viajeGuardado;
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Viaje viaje = new Viaje();
        Optional<Viaje> o = viajeRepositorio.findById(id);

        if(o.isPresent()){
            viaje = o.get();
            boolean existe = historialService.existsByViajeId(viaje.getId());

            if(existe){
                Optional<Historial> h = historialService.findByViajeId(viaje.getId());
                if(h.isPresent()) {
                    historialService.deleteHistorialByViajeId(viaje.getId());
                    auditoriaService.buildDeleteAudit(h.get());
                }
            }
            viajeRepositorio.delete(viaje);
        }
        auditoriaService.buildDeleteAudit(viaje);
    }


    @Override
    public List<ViajeDTO> listByConductorId(Long id) {

        List<Viaje> listaViajes = viajeRepositorio.findByConductorId(id);
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje : listaViajes) {
            viajeDTOList.add(convertToViajeDTO(viaje));
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
            return convertToViajeDTO(viajeResult);
        }
    }
    //CONVERTERS
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
    private ViajeDTO convertToViajeDTO(Viaje viaje) {

        ViajeDTO viajeDTO = new ViajeDTO();

        viajeDTO.setId(viaje.getId());
        viajeDTO.setRuta(viaje.getRuta());
        viajeDTO.setFecha(viaje.getFecha());
        viajeDTO.setCarro(viaje.getCarro());
        viajeDTO.setConductor(viaje.getConductor());
        viajeDTO.setEmpresaServicioNombre(viaje.getEmpresaServicioNombre());
        viajeDTO.setDadoAltaUser(viaje.getDadoAltaUser());
        viajeDTO.setDeletedDriver(viaje.getDeletedDriver());

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
   public Viaje convertToViaje(ViajeDTO viajeDTO) {

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
       viaje.setFecha(new Date());
       viaje.setConductor(viajeDTO.getConductor());
       viaje.setEmpresaServicioNombre(viajeDTO.getEmpresaServicioNombre());
       viaje.setDeletedDriver(viajeDTO.getDeletedDriver());

       return viaje;
   }

    @Override
    public List<Viaje> filtrarViajes(String numeroUnidad, Long conductorId, String fechaDesde, String fechaHasta)  {
        // Usamos la Specification para obtener los viajes filtrados
        Specification<Viaje> spec = ViajeSpecification.filtrarViajes(numeroUnidad, conductorId,fechaDesde,fechaHasta);
        return viajeRepositorio.findAll(spec, Sort.by(Sort.Direction.DESC, "fecha"));
      }

    @Override
    // Backend: Servicio para filtrar viajes con paginación
    public Page<ViajeDTO> filtrarViajesPaginados(
            String numeroUnidad, Long conductorId, String fechaDesde, String fechaHasta, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fecha"));
        Specification<Viaje> spec = ViajeSpecification.filtrarViajes(numeroUnidad, conductorId, fechaDesde, fechaHasta);

        return viajeRepositorio.findAll(spec, pageable).map(this::convertToViajeDTO);
    }



    @Override
    public List<ViajeDTO> listByCarroId(Long id) {
        List<Viaje> listViajeBD = viajeRepositorio.findByCarroIdOrderByFechaDesc(id);
        List<ViajeDTO> viajeDTOList = new ArrayList<>();

        for (Viaje viaje: listViajeBD) {
            viajeDTOList.add(convertToViajeDTO(viaje));
        }
        return viajeDTOList;
    }

    @Override
    public Page<ViajeDTO> listAllPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fecha").descending());
        return viajeRepositorio.findAll(pageable).map(this::convertToViajeDTO);
    }


    @Override
    public long countByCarroId(Long carroId) {
        return viajeRepositorio.countByCarroId(carroId);
    }
}


