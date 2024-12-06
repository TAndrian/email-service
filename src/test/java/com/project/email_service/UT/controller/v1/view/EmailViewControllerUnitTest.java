package com.project.email_service.UT.controller.v1.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.email_service.controller.v1.view.EmailViewController;
import com.project.email_service.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.email_service.controller.v1.Paths.BASE_VIEW_URL;
import static com.project.email_service.controller.v1.Paths.EMAIL_FORM_VIEW;
import static com.project.email_service.controller.v1.Paths.SEND_EMAIL;
import static com.project.email_service.controller.v1.view.EmailViewController.EMAIL_FORM;
import static com.project.email_service.controller.v1.view.EmailViewController.EMAIL_SENT;
import static com.project.email_service.util.EmailMock.emailToSend;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EmailViewController.class)
@AutoConfigureMockMvc
class EmailViewControllerUnitTest {
    public static final String EMAIL_TO_SEND_MODEL_ATTRIBUTE = "emailToSend";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailService emailService;

    private final String baseViewUrl = "/" + BASE_VIEW_URL;

    @Test
    void should_return_email_form_view() throws Exception {
        // ARRANGE
        final String URL = baseViewUrl + EMAIL_FORM_VIEW;

        // ACT
        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name(EMAIL_FORM))
                .andExpect(model().attributeExists(EMAIL_TO_SEND_MODEL_ATTRIBUTE));
    }

    @Test
    void given_email_to_send_should_send_email() throws Exception {
        // ARRANGE
        final String URL = baseViewUrl + EMAIL_FORM_VIEW + SEND_EMAIL;
        doNothing().when(emailService).sendEmail(emailToSend);

        // ACT
        mockMvc.perform(post(URL)
                        .param("emailSubject", emailToSend.emailSubject())
                        .param("emailForwarder", emailToSend.emailForwarder())
                        .param("emailContent", emailToSend.emailContent()))
                .andExpect(status().isOk())
                .andExpect(view().name(EMAIL_SENT))
                .andExpect(model().attributeExists(EMAIL_TO_SEND_MODEL_ATTRIBUTE));

        // ASSERT
        verify(emailService).sendEmail(argThat(
                dto -> dto.emailSubject().equals(emailToSend.emailSubject()) &&
                        dto.emailForwarder().equals(emailToSend.emailForwarder()) &&
                        dto.emailContent().equals(emailToSend.emailContent())
        ));
    }
}
