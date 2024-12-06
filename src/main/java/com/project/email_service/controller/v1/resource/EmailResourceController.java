package com.project.email_service.controller.v1.resource;

import com.project.email_service.dto.EmailDTO;
import com.project.email_service.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.project.email_service.controller.v1.Paths.BASE_API_URL;
import static com.project.email_service.controller.v1.Paths.SEND_EMAIL;

@RestController
@RequestMapping(
        path = BASE_API_URL,
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class EmailResourceController {

    private final EmailService emailService;

    @PostMapping(SEND_EMAIL)
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody EmailDTO emailToSend) throws MessagingException {
        emailService.sendEmail(emailToSend);
    }
}
