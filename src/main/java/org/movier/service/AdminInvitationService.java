package org.movier.service;

import jakarta.validation.constraints.NotNull;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.MyUserNotFoundException;
import org.movier.exceptions.ResultIsEmptyException;
import org.movier.model.entity.AdminInvitation;
import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.repository.AdminInvitationRepository;
import org.movier.repository.MyUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AdminInvitationService {

    private final AdminInvitationRepository adminInvitationRepository;
    private final EmailService emailService;
    private final MyUserRepository myUserRepository;
    private final AuthenticatedMyUserService authenticatedMyUserService;

    public AdminInvitationService(AdminInvitationRepository adminInvitationRepository, EmailService emailService, MyUserRepository myUserRepository, AuthenticatedMyUserService authenticatedMyUserService) {
        this.adminInvitationRepository = adminInvitationRepository;
        this.emailService = emailService;
        this.myUserRepository = myUserRepository;
        this.authenticatedMyUserService = authenticatedMyUserService;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void sendInvitation(@NotNull String username) {
        MyUser user = myUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(()-> new MyUserNotFoundException("No user found with username: "+username));

        AdminInvitation adminInvitation = new AdminInvitation();
        adminInvitation.setUser(user);

        String uuid;
        do{
            uuid = UUID.randomUUID().toString();
        }while (adminInvitationRepository.findByToken(uuid).isPresent());

        adminInvitation.setToken(uuid);
        emailService.sendAdminInvitation(adminInvitation);
        if(adminInvitationRepository.findByUser(user).isPresent()) {
            adminInvitationRepository.deleteByUser(user);
        }
        adminInvitationRepository.save(adminInvitation);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void acceptInvitation(@NotNull String token) {
        MyUser user = authenticatedMyUserService.getCurrentUserAuthenticated();

        AdminInvitation adminInvitation = adminInvitationRepository.findByToken(token)
                .orElseThrow(() -> new ResultIsEmptyException("No invitation found"));

        if(!adminInvitation.getUser().equals(user)) {
            throw new ResultIsEmptyException("No invitation found");
        }

        user.setRole(RoleEnum.ADMIN);
        myUserRepository.save(user);
        adminInvitationRepository.delete(adminInvitation);
    }

}


