package com.project.email_service.dto;

public record EmailDTO(
        String emailForwarder,
        String emailSubject,
        String emailContent
) {
}
