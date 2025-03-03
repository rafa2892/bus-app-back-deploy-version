package com.bus.app.tools.specification;

import com.bus.app.modelo.Conductor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


public class ConductorSpecification {

    public static Specification<Conductor> filtrarConductores(String nombre, String apellido, String dni) {

        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction(); // Empieza con una condición "true" (que no filtra nada)

            // Filtrar por nombre si no es null
            if (nombre != null && !nombre.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%"));
            }

            // Filtrar por apellido si no es null
            if (apellido != null && !apellido.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("apellido")), "%" + apellido.toLowerCase() + "%"));
            }

            // Filtrar por DNI si no es null
            if (dni != null && !dni.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("dni"), "%" + dni + "%"));
            }

            return predicate;
        };
    }
}
