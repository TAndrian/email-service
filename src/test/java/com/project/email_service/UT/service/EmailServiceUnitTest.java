package com.project.email_service.UT.service;

import com.project.email_service.service.impl.EmailServiceImpl;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static com.project.email_service.util.EmailMock.MOCK_FORWARDER_EMAIL;
import static com.project.email_service.util.EmailMock.RECIPIENT_EMAIL;
import static com.project.email_service.util.EmailMock.emailToSend;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceUnitTest {
    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @BeforeEach
    void setUp() {
        mimeMessage = new MimeMessage((Session) null);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void given_email_to_send_when_sendEmail_then_email_sent_successfully() throws MessagingException {
        // Arrange
        ArgumentCaptor<SimpleMailMessage> simpleMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        ArgumentCaptor<MimeMessage> mimeMessageCaptor = ArgumentCaptor.forClass(MimeMessage.class);

        // Act
        emailService.sendEmail(emailToSend);

        // Assert
        Mockito.verify(mailSender, Mockito.times(1)).send(simpleMessageCaptor.capture());

        SimpleMailMessage capturedMessage = simpleMessageCaptor.getValue();
        assertAll(
                () -> assertEquals(RECIPIENT_EMAIL, Objects.requireNonNull(capturedMessage.getTo())[0]),
                () -> assertEquals(emailToSend.emailForwarder(), capturedMessage.getFrom()),
                () -> assertEquals(emailToSend.emailSubject(), capturedMessage.getSubject()),
                () -> assertEquals(emailToSend.emailContent(), capturedMessage.getText())
        );

        Mockito.verify(mailSender, Mockito.times(1)).send(mimeMessageCaptor.capture());

        // Ensure the second email (no-reply) has the correct settings
        assertAll(
                () -> assertEquals(MOCK_FORWARDER_EMAIL,
                        mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString()),
                () -> assertEquals("RE:" + emailToSend.emailSubject(),
                        mimeMessage.getSubject())
        );
    }

}
