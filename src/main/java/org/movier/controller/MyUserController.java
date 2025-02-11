package org.movier.controller;

import org.movier.model.dto.MyUserRegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.movier.model.entity.MyUser;
import org.movier.service.MyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyUserController {

    private static final Logger logger = LoggerFactory.getLogger(MyUserController.class);
    private final MyUserService myUserService;

    public MyUserController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @GetMapping("/login")
    public String sayLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {return "register";}

    @PostMapping("/register")
    public @ResponseBody String registerUser(@Valid @RequestBody MyUserRegisterDTO user, BindingResult bindingResult) {
        logger.info("Registering user {}", user.toString());
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errorMessage.append(error.getDefaultMessage()).append("\n"));
            return "Validation failed: " + errorMessage.toString()+user.toString();
        }

        return myUserService.registerUser(user.toMyUser()) ? "success" : "fail";
    }
}
