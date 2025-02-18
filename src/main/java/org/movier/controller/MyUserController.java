package org.movier.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.model.dto.MyUserChangeDTO;
import org.movier.model.dto.MyUserRegisterDTO;
import org.movier.model.enums.RoleEnum;
import org.movier.service.MyUserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        return myUserService.registerUser(user.toMyUser(), RoleEnum.USER) ? "success" : "fail";
    }

    @GetMapping("/verify")
    public RedirectView verifyEmail(@RequestParam("token") String token) {
        boolean isVerified = myUserService.verifyEmail(token);
        if (isVerified) {
            String message = "Your email address is activated.";
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
            return new RedirectView("/login?verify=" + encodedMessage);
        } else {
            return new RedirectView("/error");
        }
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
