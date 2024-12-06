package com.project.email_service.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.project.email_service.controller.v1.Paths.BASE_API_URL;
import static com.project.email_service.controller.v1.Paths.SEND_EMAIL;
import static com.project.email_service.util.EmailMock.emailToSend;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class EmailResourceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @LocalServerPort
    private Integer port;

    private String baseUrl = "http://localhost";

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl + ":" + port + "/" + BASE_API_URL;
    }

    @Test
    void given_email_to_send_should_send_email() throws Exception {
        // ARRANGE
        final String URL = baseUrl + SEND_EMAIL;
        String requestBody = objectMapper.writeValueAsString(emailToSend);

        // ACT
        mockMvc.perform(post(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}
