package org.movier.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.movier.model.dto.MyUserRegisterDTO;
import org.movier.service.AdminInvitationService;
import org.movier.service.MyUserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private final MyUserService myUserService;
    private final AdminInvitationService adminInvitationService;

    public AdminController(MyUserService myUserService, AdminInvitationService adminInvitationService) {
        this.myUserService = myUserService;
        this.adminInvitationService = adminInvitationService;
    }

    @PostMapping("/addAdmin")
    public String addAdmin(@NotNull @RequestBody String username) {
        adminInvitationService.sendInvitation(username);
        return  "Invitation sent";
    }

    @PostMapping("/banUser")
    public String banUser(@NotNull @RequestBody String username) {
        return myUserService.banUserByUsername(username) ? "User banned" : "failed";
    }

    @PostMapping("/unbanUser")
    public String unbanUser(@NotNull @RequestBody String username) {
        return myUserService.unbanUserByUsername(username) ? "User unbanned" : "failed";
    }

    @PostMapping("/newAdminRegister")
    public String newAdminRegister(@NotNull @RequestParam("token") String token ,@Valid @RequestBody MyUserRegisterDTO user) {
        adminInvitationService.acceptInvitation(token, user.toMyUser());
        return "success";
    }
}
