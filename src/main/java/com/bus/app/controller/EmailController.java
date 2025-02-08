package com.bus.app.controller;

import com.bus.app.services.EmailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarCorreoConExcel(@RequestParam String destinatario) {
        try {
            emailService.enviarExcelPorCorreo();
            return ResponseEntity.ok("Correo enviado correctamente a " + destinatario);
        } catch (MessagingException | IOException e) {
            String msjErrorLogger = "Error al enviar el correo: ";
            logger.error(msjErrorLogger, e);
            return ResponseEntity.status(500).body(msjErrorLogger + e.getMessage());
        }
    }
}
