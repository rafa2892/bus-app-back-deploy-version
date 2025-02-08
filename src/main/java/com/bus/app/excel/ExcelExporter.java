package com.bus.app.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ExcelExporter<T> {
    Map<String, Object> obtenerEstructuraExcelPorTipo(List<T> datos);
    void rellenarFilas(T data, Row row, CellStyle celdaEstilos);
}