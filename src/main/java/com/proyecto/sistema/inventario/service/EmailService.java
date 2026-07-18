package com.proyecto.sistema.inventario.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

// import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${mail.urlFront}")
    private String urlFront;

    public void enviarCorreoRecuperacion(String mailTo, String nombre, String token) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Map<String, Object> model = new HashMap<>();
            model.put("nombre", nombre);
            model.put("url", urlFront + token);

            Context context = new Context();
            context.setVariables(model);
            String htmlText = templateEngine.process("email-recuperacion", context);

            helper.setFrom(mailFrom);
            helper.setTo(mailTo);
            helper.setSubject("Recuperación de contraseña");
            helper.setText(htmlText, true);

            javaMailSender.send(message);
        } catch (Exception e) {

        }
    }
}