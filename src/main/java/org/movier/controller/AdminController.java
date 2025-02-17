package org.movier.controller;

import jakarta.validation.constraints.NotNull;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.UserDontHaveRightsForActionException;
import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.service.AdminInvitationService;
import org.movier.service.MyUserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private final MyUserService myUserService;
    private final AuthenticatedMyUserService authenticatedMyUserService;
    private final AdminInvitationService adminInvitationService;

    public AdminController(MyUserService myUserService, AuthenticatedMyUserService authenticatedMyUserService, AdminInvitationService adminInvitationService) {
        this.myUserService = myUserService;
        this.authenticatedMyUserService = authenticatedMyUserService;
        this.adminInvitationService = adminInvitationService;
    }

    private void isUserIsAdmin() {
        MyUser user = authenticatedMyUserService.getCurrentUserAuthenticated();
        if (user.getRole()!= RoleEnum.ADMIN){
            throw new UserDontHaveRightsForActionException("You are not allowed to do this action");
        }
    }

    @PostMapping("/addAdmin")
    public String addAdmin(@NotNull @RequestBody String username) {
        isUserIsAdmin();
        adminInvitationService.sendInvitation(username);
        return  "Invitation sent";
    }

    @PostMapping("/banUser")
    public String banUser(@NotNull @RequestBody String username) {
        isUserIsAdmin();
        return myUserService.banUserByUsername(username) ? "User banned" : "failed";
    }

    @PostMapping("/unbanUser")
    public String unbanUser(@NotNull @RequestBody String username) {
        isUserIsAdmin();
        return myUserService.unbanUserByUsername(username) ? "User unbanned" : "failed";
    }

    @GetMapping("/accept")
    public String verifyEmail(@RequestParam("token") String token) {
        adminInvitationService.acceptInvitation(token);
        return "success";
    }
}
