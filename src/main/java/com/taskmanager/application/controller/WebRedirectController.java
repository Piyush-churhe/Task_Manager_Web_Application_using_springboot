package com.taskmanager.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebRedirectController {
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login.html";
    }
}
