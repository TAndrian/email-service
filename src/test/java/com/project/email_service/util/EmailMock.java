package com.project.email_service.util;

import com.project.email_service.dto.EmailDTO;

public class EmailMock {
    public static final String RECIPIENT_EMAIL = "t.andrian.rak@gmail.com";
    public static final String MOCK_FORWARDER_EMAIL = "test.forwarder@example.com";
    public static EmailDTO emailToSend = new EmailDTO("test.forwarder@example.com", "Test Subject", "Test Content");
}
