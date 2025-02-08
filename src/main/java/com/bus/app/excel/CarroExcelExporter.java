package com.bus.app.excel;

import com.bus.app.modelo.Carro;
import com.bus.app.tools.BusAppUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarroExcelExporter implements ExcelExporter<Carro> {

    @Override
    public Map<String, Object> obtenerEstructuraExcelPorTipo(List<Carro> carros) {

        String[] headers = {"Num.Unidad","Modelo","T.Vehiculo", "Año", "Consumo l/100", "Fecha registro"};
        List<String> fieldNames = Arrays.stream(headers).toList();
        Map<String,Object> estructuraExcel = new HashMap<>();

        estructuraExcel.put("headers", headers);
        estructuraExcel.put("entidades", carros);
        estructuraExcel.put("fieldNames",fieldNames);

        return estructuraExcel;
    }

    @Override
    public void rellenarFilas(Carro carro, Row row, CellStyle rowStyle) {

        // Llenar la celda de la fecha
        row.createCell(0).setCellValue(BusAppUtils.getNumeroUnidadFormateado(carro.getNumeroUnidad()));
        row.getCell(0).setCellStyle(rowStyle);

        // Llenar la celda de la fecha
        String carroModeloMarca = carro.getMarca().concat(" ").concat(carro.getModelo());
        row.createCell(1).setCellValue(carroModeloMarca);
        row.getCell(1).setCellStyle(rowStyle);

        row.createCell(2).setCellValue(carro.getTipoVehiculo());
        row.getCell(2).setCellStyle(rowStyle);

        row.createCell(3).setCellValue(carro.getAnyo());
        row.getCell(3).setCellStyle(rowStyle);

        String consumo = "NO DATA";
        if(carro.getConsumo() != null) {
            consumo = carro.getConsumo().toString();
        }
        row.createCell(4).setCellValue(consumo);
        row.getCell(4).setCellStyle(rowStyle);

        row.createCell(5).setCellValue(carro.getFechaAlta());
        row.getCell(5).setCellStyle(rowStyle);
    }
}
