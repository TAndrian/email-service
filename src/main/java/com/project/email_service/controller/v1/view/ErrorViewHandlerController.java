package com.project.email_service.controller.v1.view;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorViewHandlerController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletResponse response) {
        int statusCode = response.getStatus();
        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return "error/404";
        }

        return "error/500";
    }
}
