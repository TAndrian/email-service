package com.project.email_service.service;

import com.project.email_service.dto.EmailDTO;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(EmailDTO emailToSend) throws MessagingException;

}
