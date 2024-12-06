package com.project.email_service.service.impl;

import com.project.email_service.dto.EmailDTO;
import com.project.email_service.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import static com.project.email_service.util.EmailStaticMessageUtil.NO_REPLY_CONTENT;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    public static final String MY_EMAIL = "t.andrian.rak@gmail.com";
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(EmailDTO emailToSend) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        String emailForwarder = emailToSend.emailForwarder();
        String emailSubject = emailToSend.emailSubject();

        message.setTo(MY_EMAIL);
        message.setFrom(emailForwarder);
        message.setSubject(emailSubject);
        message.setText(emailToSend.emailContent());
        mailSender.send(message);
        sendNoReplyEmail(emailForwarder, emailSubject);
    }

    /**
     * Send a no-reply message to the forwarder.
     *
     * @param emailRecipient recipient's email alias the forwarder's email.
     * @param emailSubject   the forwarder's email subject.
     * @throws MessagingException exception.
     */
    private void sendNoReplyEmail(String emailRecipient, String emailSubject) throws MessagingException {
        String replySubject = "RE:" + emailSubject;

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setFrom(MY_EMAIL);
        helper.setTo(emailRecipient);
        helper.setReplyTo(emailRecipient);
        helper.setSubject(replySubject);
        helper.setText(NO_REPLY_CONTENT, true);
        mailSender.send(mimeMessage);
    }
}
