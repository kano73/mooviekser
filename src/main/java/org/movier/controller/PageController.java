package org.movier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/login")
    public String sayLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {return "register";}
}
