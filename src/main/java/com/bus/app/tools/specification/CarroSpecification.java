package com.bus.app.tools.specification;

import com.bus.app.modelo.Carro;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CarroSpecification {

    public static Specification<Carro> filtrarCarros(String marca, String modelo, Long anyo, Long numeroUnidad) {

        return (root, query, criteriaBuilder) -> {

            Predicate predicate = criteriaBuilder.conjunction(); // Empieza con una condición "true" (que no filtra nada)

            // Filtrar por modelo si no es null
            if (modelo != null && !modelo.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("modelo")), "%" + modelo.toLowerCase() + "%"));
            }

            // Filtrar por marca si no es null
            if (marca != null && !marca.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(criteriaBuilder.lower(root.get("marca")), "%" + marca.toLowerCase() + "%"));
            }

            // Filtrar por año si no es null
            if (anyo != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.toString(root.get("anyo")), anyo + "%"
                        )
                );
            }

            // Filtrar por número de unidad si no es null (buscando si empieza con el número ingresado)
            if (numeroUnidad != null) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.toString(root.get("numeroUnidad")), numeroUnidad + "%"
                        )
                );
            }


            return predicate;
        };
    }
}
