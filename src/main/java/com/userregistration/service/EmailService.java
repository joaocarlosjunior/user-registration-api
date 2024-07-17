package com.userregistration.service;

import com.userregistration.domain.entites.ConfirmationToken;
import com.userregistration.domain.entites.User;
import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;

import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendEmail(SimpleMailMessage email);

    void sendVerificationEmail(User user, ConfirmationToken confirmationToken, String siteUrl)
            throws MessagingException, UnsupportedEncodingException;
}
