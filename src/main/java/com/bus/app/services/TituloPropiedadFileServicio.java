package com.bus.app.services;

import com.bus.app.modelo.TituloPropiedadFile;
import com.bus.app.repositorio.TituloPropiedadFileRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TituloPropiedadFileServicio {

    @Autowired
    private TituloPropiedadFileRepositorio archivoRepository;

    public TituloPropiedadFile guardarArchivo(MultipartFile file) throws Exception {
        TituloPropiedadFile archivo = new TituloPropiedadFile();
        archivo.setNombre(file.getOriginalFilename());
        archivo.setTipo(file.getContentType());
//        archivo.setDatos(file.getBytes());
        return archivoRepository.save(archivo);
    }

    public TituloPropiedadFile obtenerArchivo(Long id) {
        return archivoRepository.findById(id).orElse(null);
    }
}
