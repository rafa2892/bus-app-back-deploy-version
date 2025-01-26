package com.bus.app.specification;

import com.bus.app.modelo.Viaje;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViajeSpecification {

    public static Specification<Viaje> filtrarViajes(String numeroUnidad, Long conductorId, String fechaDesde, String fechaHasta) {

        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction(); // Empieza con una condición "true" (que no filtra nada)

            // Filtrar por numeroUnidad si no es null
            if (numeroUnidad != null && !numeroUnidad.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("carro").get("numeroUnidad"), numeroUnidad));
            }

            // Filtrar por conductorId si no es null
            if (conductorId != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("conductor").get("id"), conductorId));
            }

            // Filtrar por fechas entre fechaDesde y fechaHasta
            if (fechaDesde != null && !fechaDesde.isEmpty() && fechaHasta != null && !fechaHasta.isEmpty()) {
                try {
                    // Convertir fechaDesde y fechaHasta a Date
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Date fechaDesdeDate = dateFormat.parse(fechaDesde);
                    Date fechaHastaDate = dateFormat.parse(fechaHasta);

                    fechaDesdeDate = resetTime(fechaDesdeDate, false); // Resetear a medianoche
                    fechaHastaDate = resetTime(fechaHastaDate, true); // Ajustar al final del día

                    // Filtrar los viajes dentro del rango de fechas usando el campo correcto `fechaViaje`
                    predicate = criteriaBuilder.and(
                            predicate,
                            criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), fechaDesdeDate),
                            criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), fechaHastaDate)
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Filtrar por vehiculoId si no es null
            return predicate;
        };
    }

    // Función para resetear la hora de la fecha
    private static Date resetTime(Date date, boolean endOfDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (endOfDay) {
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        return cal.getTime();
    }


}
