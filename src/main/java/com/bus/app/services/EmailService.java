package com.bus.app.services;

import com.bus.app.tools.BusAppUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ExcelService excelService;

    public void enviarExcelPorCorreo() throws MessagingException, IOException {
        enviarExcelPorCorreoProgramado();
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void enviarExcelPorCorreoProgramado() throws MessagingException, IOException {

        // 1️⃣ Generar el archivo Excel
//        byte[] excelBytes = excelService.generarExcel("dailyMail", null, null);

        // 2️⃣ Crear el mensaje de correo
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo("busappcontador@gmail.com");
        helper.setSubject("Reporte diario automático, servicios " + BusAppUtils.fechaFormateada(new Date()));
        helper.setText("Adjunto documento con los registros del dia.");

        // 3️⃣ Adjuntar el archivo Excel
//        InputStreamSource archivoAdjunto = new ByteArrayResource(excelBytes);
//        helper.addAttachment("servicios_"+ BusAppUtils.fechaFormateada(new Date()) + ".xlsx", archivoAdjunto);

        // 4️⃣ Enviar el correo
        mailSender.send(mensaje);
    }
}
