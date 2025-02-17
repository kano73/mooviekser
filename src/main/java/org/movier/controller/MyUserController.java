package org.movier.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.model.dto.MyUserChangeDTO;
import org.movier.model.dto.MyUserRegisterDTO;
import org.movier.service.MyUserService;
import org.springframework.web.bind.annotation.*;


@RestController
public class MyUserController {

    private final MyUserService myUserService;
    private final AuthenticatedMyUserService authenticatedMyUserService;

    public MyUserController(MyUserService myUserService, AuthenticatedMyUserService authenticatedMyUserService) {
        this.myUserService = myUserService;
        this.authenticatedMyUserService = authenticatedMyUserService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody MyUserRegisterDTO user) {
        return myUserService.registerUser(user.toMyUser()) ? "success" : "fail";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
        return myUserService.verifyEmail(token) ? "success" : "fail";
    }

    @PostMapping("/change_profile")
    public String changeProfile(@RequestBody @Valid MyUserChangeDTO dto, HttpServletRequest request, HttpServletResponse response){
        if(myUserService.updateUser(dto)){
            authenticatedMyUserService.logoutCurrentUser(request, response);
            return "success";
        }else{
            return "fail";
        }
    }
}
