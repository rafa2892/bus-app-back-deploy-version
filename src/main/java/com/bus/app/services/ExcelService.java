package com.bus.app.services;

import com.bus.app.constantes.Constantes;
import com.bus.app.excel.CarroExcelExporter;
import com.bus.app.excel.ConductorExcelExporter;
import com.bus.app.excel.ExcelExporter;
import com.bus.app.excel.ViajeExcelExporter;
import com.bus.app.repositorio.CarroRepositorio;
import com.bus.app.repositorio.ConductorRepositorio;
import com.bus.app.repositorio.ViajeRepositorio;
import com.bus.app.tools.BusAppUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ExcelService {

    @Autowired
    private ViajeRepositorio viajeRepo;
    @Autowired
    private CarroRepositorio carroRepo;
    @Autowired
    private ConductorRepositorio conduRepo;

    private Date fechaInicio = null;
    private Date fechaHasta = null;


    public ExcelService() {
    }

    @SuppressWarnings("unchecked")
    public <T> byte[] generarExcel(String tipo, Date fechaDesde, Date fechaHasta) throws IOException {

        if(tipo.equalsIgnoreCase("viajesPorFechas")) {
            Date fechaHastaAjustada = BusAppUtils.ajustarFechaHasta(fechaHasta);
            this.fechaInicio = fechaDesde;
            this.fechaHasta = fechaHastaAjustada;
        }

        /* ---- Obtenemos los valores de las fechas, según tipo ----*/
        else {
            Map<String, Date> fechasAjustadas = setFormatDatesByType(tipo,fechaDesde,fechaHasta);
            this.fechaInicio = fechasAjustadas.get("fechaDesde");
            this.fechaHasta = fechasAjustadas.get("fechaHasta");
        }

        @SuppressWarnings("unchecked")
        List<T> datos = switch (tipo) {
            case "carros" -> (List<T>) carroRepo.findAll();  // List<Carro>
            case "conductores" -> (List<T>)conduRepo.findAll();  // List<Conductor>
            case "viajes" -> (List<T>) viajeRepo.findAllByOrderByFechaDesc();  // List<Viaje>
            case "dailyMail" -> (List<T>) viajeRepo.findByFechaHoy();  // List<Viaje>
            case "viajesPorFechas", "viajesDiaEspecifico", "hoy", "ayer" -> (List<T>) viajeRepo.findByFechaBetweenOrderByFechaDesc(this.fechaInicio, this.fechaHasta);
            default -> throw new IllegalArgumentException("No hay exportador para: " + tipo);
        };

        ExcelExporter exportador = switch (tipo) {
            case "carros" -> new CarroExcelExporter();
            case "conductores" -> new ConductorExcelExporter();
            case "viajes", "dailyMail", "viajesPorFechas", "viajesDiaEspecifico", "hoy", "ayer" -> new ViajeExcelExporter();
            default -> throw new IllegalArgumentException("No hay exportador para: " + tipo);
        };

        Map<String, Object> parametrosExcel =
                exportador.obtenerEstructuraExcelPorTipo(datos);

        // Generar el archivo Excel y escribirlo en un ByteArrayOutputStream
        try (Workbook workbook = buildExcel(parametrosExcel, exportador, tipo);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
             workbook.write(out);
             return out.toByteArray();
        }
    }

    Map<String, Date> setFormatDatesByType(String tipo, Date fechaDesde, Date fechaHasta) {
        Map<String, Date> fechasAjustadas = new HashMap<>();

        if(tipo.equalsIgnoreCase("viajesDiaEspecifico")) {
            fechasAjustadas = BusAppUtils.ajustarFechaDiaEspecifico(fechaDesde, fechaHasta);
        }

        if(tipo.equalsIgnoreCase("hoy")) {
            fechasAjustadas = BusAppUtils.ajustarFechaDiaEspecifico(new Date(), new Date());
        }

        if(tipo.equalsIgnoreCase("ayer")) {
            LocalDate ayer = LocalDate.now().minusDays(1);
            Date ayerInicioDate = Date.from(ayer.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date ayerHastaDate = Date.from(ayer.atStartOfDay(ZoneId.systemDefault()).toInstant());
            fechasAjustadas = BusAppUtils.ajustarFechaDiaEspecifico(ayerInicioDate, ayerHastaDate);
        }
        return fechasAjustadas;
    }

    public <T> Workbook buildExcel(Map<String, Object> parametrosExcel, ExcelExporter<T> exportador, String tipo) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Datos");

        String[] headers  = (String[]) parametrosExcel.get("headers");
        List<T> entidades = (List<T>) parametrosExcel.get("entidades");
        List<String> fieldNames = (List<String>) parametrosExcel.get("fieldNames");

        // ---- Agregar el título en la fila 0 ----
        Row tituloRow = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1)); // Fusionar celdas para el título

        String titulo = Constantes.TITULO_EXCEL_EXPORTACION.get(tipo);

        if ("viajesPorFechas".equals(tipo)) {
            titulo = String.format(titulo,
                    BusAppUtils.fechaFormateada(this.fechaInicio),
                    BusAppUtils.fechaFormateada(this.fechaHasta));
        }

        if ("hoy".equals(tipo)) {
            titulo = String.format(titulo,
                    BusAppUtils.fechaFormateada(this.fechaInicio));
        }

        if ("ayer".equals(tipo)) {
            titulo = String.format(titulo,
                    BusAppUtils.fechaFormateada(this.fechaInicio));
        }

        if ("viajesDiaEspecifico".equals(tipo)) {
            titulo = String.format(titulo,
                    BusAppUtils.fechaFormateada(this.fechaInicio));
        }



        Cell tituloCell = tituloRow.createCell(0);
        tituloCell.setCellValue(titulo); // Texto del título
        tituloCell.setCellStyle(getTitleCellStyle(workbook)); // Aplica el estilo del título

        // ----Crear el encabezado----
        Row headerRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));  // Aplica el estilo del encabezado
        }

        // ----Agregar los datos----
        int rowNum = 2;
        for (T entidad : entidades) {
            Row row = sheet.createRow(rowNum);

            // Alternar el color de las filas
            CellStyle rowStyle = (rowNum % 2 == 0)
                    ? getRowStyleWithColor(workbook, "#8ccaf3")  // Color fila par (naranja)
                    : getRowStyle(workbook, IndexedColors.WHITE.getIndex());  // Fila impar blanca

            exportador.rellenarFilas(entidad,row,rowStyle);
            rowNum++;
        }
        // Auto-ajustar columnas
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        return workbook;
    }

    //----Estilos titulo excel----
    private CellStyle getTitleCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        font.setColor(IndexedColors.BLUE.getIndex());

        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex()); // Fondo azul oscuro
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex()); // Letra blanca
        font.setBold(true); // Texto en negrita
        style.setFont(font);

        return style;
    }

    private CellStyle getRowStyleWithColor(Workbook workbook, String hexColor) {
        XSSFColor color = new XSSFColor(java.awt.Color.decode(hexColor), null);
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    private CellStyle getRowStyle(Workbook workbook, short colorIndex) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(colorIndex);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }


}
