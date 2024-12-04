package com.project.email_service.controller.v1.view;

import com.project.email_service.dto.EmailDTO;
import com.project.email_service.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.email_service.controller.v1.Paths.BASE_VIEW_URL;
import static com.project.email_service.controller.v1.Paths.EMAIL_FORM_VIEW;
import static com.project.email_service.controller.v1.Paths.SEND_EMAIL;

@Controller
@RequestMapping(BASE_VIEW_URL)
@AllArgsConstructor
public class EmailViewController {

    public static final String EMAIL_FORM = "email-form";
    public static final String EMAIL_SEND = "email-send";
    private EmailService emailService;

    @GetMapping(EMAIL_FORM_VIEW)
    public String emailFormView(Model model) {

        // Empty email DTO to store data from the form.
        EmailDTO emailToSend = new EmailDTO(null, null, null);

        model.addAttribute("emailToSend", emailToSend);
        return EMAIL_FORM;
    }

    @PostMapping(EMAIL_FORM_VIEW + SEND_EMAIL)
    public String sendEmailView(
            Model model,
            @ModelAttribute("emailToSend") EmailDTO emailToSend) throws MessagingException {

        emailService.sendEmail(emailToSend);
        return EMAIL_SEND;
    }
}
