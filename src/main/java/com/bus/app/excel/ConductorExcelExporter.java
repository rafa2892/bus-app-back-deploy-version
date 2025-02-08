package com.bus.app.excel;

import com.bus.app.modelo.Conductor;
import com.bus.app.tools.BusAppUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConductorExcelExporter implements ExcelExporter<Conductor> {

    @Override
    public Map<String, Object> obtenerEstructuraExcelPorTipo(List<Conductor> conductores) {

        String[] headers = {"Nombre","C.I","Fecha registro"};
        List<String> fieldNames = Arrays.stream(headers).toList();
        Map<String,Object> estructuraExcel = new HashMap<>();

        estructuraExcel.put("headers", headers);
        estructuraExcel.put("entidades", conductores);
        estructuraExcel.put("fieldNames",fieldNames);

        return estructuraExcel;
    }

    @Override
    public void rellenarFilas(Conductor conductor, Row row, CellStyle rowStyle) {

        // Llenar la celda de la fecha
        String nombre = conductor.getNombre().concat(" ").concat(conductor.getApellido());
        row.createCell(0).setCellValue(nombre);
        row.getCell(0).setCellStyle(rowStyle);

        // Llenar la celda de la fecha
        row.createCell(1).setCellValue(conductor.getDni());
        row.getCell(1).setCellStyle(rowStyle);

        //Convertimos fecha
        String fecha = BusAppUtils.fechaFormateada(conductor.getFechaAlta());  // Obtener la fecha del viaje
        row.createCell(2).setCellValue(fecha);
        row.getCell(2).setCellStyle(rowStyle);

    }
}
