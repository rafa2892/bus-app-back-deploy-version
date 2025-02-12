package com.bus.app.constantes;

import com.bus.app.tools.BusAppUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Constantes {

    public final static String REGISTRO_VIAJE = "Nuevo viaje registrado";
    public final static Long REGISTRO_VIAJE_ID = 1L ;
    public final static String REPARACION = "Mantenimiento/Reparación";
    public final static Long REPARACION_ID = 2L ;
    public final static String OTROS = "Otros/Comentarios/Actividades";
    public final static Long OTROS_ID = 3L;

    /*Registro de actividades */
    public final static String ELIMINACION_CONDUCTOR = "Se ha eliminado un conductor";

    public final static String MENSAJE_GENERICO_DELETE_AUDITORIA = "SE HA ELIMINADO UN/A : ";

    //Tipos de actividades
    public final static Map<String, Long> TIPOS_AUDIT = Map.of(
            "POST", 1L,
            "PUT", 2L,
            "DELETE", 3L
    );

    public final static Map<String, String> TITULO_EXCEL_EXPORTACION = Map.of(
            "viajes", "Lista servicios",
            "carros", "Vehículos regitrados",
            "conductores", "Conductores registrados",
            "dailyMail", "Envío automatizado de correos, servicios del dia : "
                    + BusAppUtils.fechaFormateada(new Date()),
            "viajesPorFechas", "Servicios entre fechas: %s -- %s",
            "hoy", "Servicios fecha: %s ",
            "ayer", "Servicios fecha: %s ",
            "viajesDiaEspecifico", "Servicios fecha: %s "

    );

    public static Map<Long,String> getTiposHistoriales() {
        Map<Long, String> datos = new HashMap<>();
        datos.put(Constantes.REGISTRO_VIAJE_ID, Constantes.REGISTRO_VIAJE);
        datos.put(Constantes.REPARACION_ID, Constantes.REPARACION);
        datos.put(Constantes.OTROS_ID, Constantes.OTROS);
        return datos;
    }


}
