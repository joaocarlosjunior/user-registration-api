package com.userregistration.service.impl;

import com.userregistration.domain.entites.ConfirmationToken;
import com.userregistration.domain.entites.User;
import com.userregistration.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("spring.mail.username")
    private String fromAddress;

    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void sendVerificationEmail(User user, ConfirmationToken confirmationToken, String siteUrl)
            throws MessagingException, UnsupportedEncodingException {
        String senderName = "João Carlos";
        String subject = "Verificar seu cadastro";

        String content = emailContent();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper= new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);

        String fullName = user.getFirstName() + " " + user.getLastName();
        content = content.replace("[[name]]", fullName);

        String verifyURL = siteUrl + "/auth/verify?code=" + confirmationToken.getConfirmationToken();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    private String emailContent(){
        return "Olá [[name]],<br>"
                + "Por favor, clique no link para verificar seu cadastro:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFICAR</a></h3>"
                + "Obrigado,<br>"
                + "João carlos.";
    }
}
