package org.movier.controller;

import org.movier.model.details.MyUserDetails;
import org.movier.service.PageBuilderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final PageBuilderService pageBuilderService;

    public PageController(PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String register() {return "register";}
}
