package com.example.demo.controller;

import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final ProductRepository productRepository;
    private final String contactFormEndpoint;

    public PageController(ProductRepository productRepository,
                          @Value("${contact.form.endpoint:}") String contactFormEndpoint) {
        this.productRepository = productRepository;
        this.contactFormEndpoint = contactFormEndpoint;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("featuredProducts", productRepository.findByFeaturedTrue());
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
