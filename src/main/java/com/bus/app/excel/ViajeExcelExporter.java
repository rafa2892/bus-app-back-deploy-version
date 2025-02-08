package com.bus.app.excel;

import com.bus.app.modelo.Viaje;
import com.bus.app.repositorio.CarroRepositorio;
import com.bus.app.tools.BusAppUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ViajeExcelExporter implements ExcelExporter<Viaje> {

    @Override
    public Map<String, Object> obtenerEstructuraExcelPorTipo(List<Viaje> viajes) {

        String[] headers = {"Fecha","Hora registro",  "Num.Unidad", "Modelo unidad", "Nombre conductor", "Origen", "Destino", "Empresa servicio"};
        List<String> fieldNames = Arrays.stream(headers).toList();
        Map<String,Object> estructuraExcel = new HashMap<>();

        estructuraExcel.put("headers", headers);
        estructuraExcel.put("entidades", viajes);
        estructuraExcel.put("fieldNames",fieldNames);

        return estructuraExcel;
    }

    @Override
    public void rellenarFilas(Viaje viaje, Row row,CellStyle rowStyle) {

        //Convertimos fecha
        Date fecha = viaje.getFecha(); // Obtener la fecha del viaje
        String fechaFormateada = BusAppUtils.fechaFormateada(fecha);
        String hora = BusAppUtils.horaFormateada(fecha);

        // Llenar la celda de la fecha
        row.createCell(0).setCellValue(fechaFormateada);
        row.getCell(0).setCellStyle(rowStyle);

        //Hora de registro
        row.createCell(1).setCellValue(hora);
        row.getCell(1).setCellStyle(rowStyle);

        // Llenar la celda de número de unidad

        Cell celdaNumUni = row.createCell(2);
        celdaNumUni.setCellValue(BusAppUtils.getNumeroUnidadFormateado(viaje.getCarro().getNumeroUnidad()));

        // Componer el estilo con rowStyle (intercalado) y el estilo de fuente naranja y negrita
        CellStyle combinedStyle = row.getSheet().getWorkbook().createCellStyle();
        combinedStyle.cloneStyleFrom(rowStyle);

        // Cambiar el estilo a negrita y color naranja
        setFontColorStyle(row.getSheet().getWorkbook(), IndexedColors.ORANGE, combinedStyle);
        celdaNumUni.setCellStyle(combinedStyle);  // Aplica el estilo de fuente naranja y negrita

        // Llenar la celda de modelo de unidad
        String marcaConModeloVehiculo = viaje.getCarro().getMarca().concat(" ").concat(viaje.getCarro().getModelo());
        row.createCell(3).setCellValue(marcaConModeloVehiculo);
        row.getCell(3).setCellStyle(rowStyle);

        // Llenar la celda del nombre del conductor
        String nombreConductor = "NO DATA";
        if (viaje.getConductor() != null) {
            nombreConductor = viaje.getConductor().getNombre().concat(" ").concat(viaje.getConductor().getApellido());
        } else if (viaje.getDeletedDriver() != null) {
            nombreConductor = viaje.getDeletedDriver();
        }
        row.createCell(4).setCellValue(nombreConductor);
        row.getCell(4).setCellStyle(rowStyle);

        // Llenar las celdas de la ruta origen y destino
        String rutaOrigen = viaje.getRuta().getEstadoOrigen().concat(",  ").concat(viaje.getRuta().getCiudadOrigen());
        String rutaDestino = viaje.getRuta().getEstadoDestino().concat(",  ").concat(viaje.getRuta().getCiudadDestino());

        row.createCell(5).setCellValue(rutaOrigen);
        row.getCell(5).setCellStyle(rowStyle);

        row.createCell(6).setCellValue(rutaDestino);
        row.getCell(6).setCellStyle(rowStyle);

        // Llenar la celda de la empresa de servicio
        row.createCell(7).setCellValue(viaje.getEmpresaServicioNombre());
        row.getCell(7).setCellStyle(rowStyle);
    }

    private void setFontColorStyle(Workbook workbook, IndexedColors color, CellStyle combinedStyle) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setColor(color.getIndex()); // Cambia el color de la fuente
        font.setBold(true); // Opcional: Hace el texto en negrita
        style.setFont(font);

        combinedStyle.setFont(font);
    }
}
