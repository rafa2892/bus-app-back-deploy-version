package com.bus.app.controller;

import com.bus.app.services.ExcelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    private static final Logger logger = LogManager.getLogger(ExcelController.class.getName());


    @GetMapping("/download/{tipo}")
    public ResponseEntity<byte[]> descargarExcel(
            @PathVariable String tipo,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin) {
            try {
                byte[] excelBytes = excelService.generarExcel(tipo,fechaInicio,fechaFin);
                HttpHeaders headers = new HttpHeaders();

                // Configuramos el nombre del archivo en la cabecera
                headers.setContentDispositionFormData("attachment", "excel-export-" + "tipo_".toLowerCase() + ".xlsx");
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(excelBytes);
            } catch (Exception e) {
                logger.error("Ocurrió un error al intentar descargar excel: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
    }
}
