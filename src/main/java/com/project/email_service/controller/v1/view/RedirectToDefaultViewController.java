package com.project.email_service.controller.v1.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.email_service.controller.v1.Paths.BASE_VIEW_URL;
import static com.project.email_service.controller.v1.Paths.EMAIL_FORM_VIEW;

@Controller
public class RedirectToDefaultViewController {
    @RequestMapping("/")
    public String redirectToForm() {
        return "redirect:/" + BASE_VIEW_URL + EMAIL_FORM_VIEW;
    }
}
