package com.bus.app.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BusAppUtils {

    private static final Logger logger = LogManager.getLogger(BusAppUtils.class);

    public static String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // Obtener el nombre de usuario del objeto Authentication
            username = authentication.getName();
            logger.info("Datos del usuario : {}", username);
        }
        return username;
    }
}