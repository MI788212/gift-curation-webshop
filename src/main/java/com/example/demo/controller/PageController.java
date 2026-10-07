package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final String contactFormEndpoint;

    public PageController(@Value("${contact.form.endpoint:}") String contactFormEndpoint) {
        this.contactFormEndpoint = contactFormEndpoint;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/news")
    public String news() {
        return "news";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactFormEndpoint", contactFormEndpoint);
        return "contact";
    }
}
