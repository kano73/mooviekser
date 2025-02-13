package org.movier.controller;

import org.movier.model.dto.MyUserRegisterDTO;
import org.movier.service.EmailValidationService;
import org.movier.model.entity.MyUser;
import org.movier.service.MyUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyUserController {

    private final MyUserService myUserService;
    private final EmailValidationService emailValidationService;

    public MyUserController(MyUserService myUserService, EmailValidationService emailValidationService) {
        this.myUserService = myUserService;
        this.emailValidationService = emailValidationService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody MyUserRegisterDTO user) {
        return myUserService.registerUser(user.toMyUser()) ? "success" : "fail";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        MyUser user = emailValidationService.findByToken(token);
        if (user!=null) {
            myUserService.verifyEmail(user);
            return "success";
        }else{
            return "link is not valid";
        }
    }
}
