package com.bus.app.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * Obtiene la fecha actual en formato DD/MM/YYYY HH:mm:ss
     *
     * @return La fecha actual en formato DD/MM/YYYY HH:mm:ss
     */
    public static String getFechaActual() {
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(fecha);
    }

    /**
     * Convierte una fecha en formato DD/MM/YYYY HH:mm:ss
     *
     * @param date La fecha a formatear
     * @return La fecha en formato deseado
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
    }
}
