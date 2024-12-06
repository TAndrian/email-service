package com.project.email_service.UT.controller.v1.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.email_service.controller.v1.resource.EmailResourceController;
import com.project.email_service.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.email_service.controller.v1.Paths.BASE_API_URL;
import static com.project.email_service.controller.v1.Paths.SEND_EMAIL;
import static com.project.email_service.util.EmailMock.emailToSend;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailResourceController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class EmailResourceControllerUnitTest {

    @MockBean
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void given_email_to_send_when_sendEmail_then_return_OK_status() throws Exception {
        // ARRANGE
        final String URL = "/" + BASE_API_URL + SEND_EMAIL;
        String requestBody = objectMapper.writeValueAsString(emailToSend);
        doNothing().when(emailService).sendEmail(emailToSend);

        // ACT
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        // ASSERT
        verify(emailService, times(1)).sendEmail(emailToSend);
    }
}
