package org.movier.service;

import jakarta.validation.constraints.NotNull;
import org.movier.exceptions.MyUserNotFoundException;
import org.movier.exceptions.NoInvitationException;
import org.movier.model.entity.AdminInvitation;
import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.repository.AdminInvitationRepository;
import org.movier.repository.MyUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AdminInvitationService {

    private final AdminInvitationRepository adminInvitationRepository;
    private final EmailService emailService;
    private final MyUserRepository myUserRepository;
    private final MyUserService myUserService;

    public AdminInvitationService(AdminInvitationRepository adminInvitationRepository,
                                  EmailService emailService,
                                  MyUserRepository myUserRepository,
                                  MyUserService myUserService) {
        this.adminInvitationRepository = adminInvitationRepository;
        this.emailService = emailService;
        this.myUserRepository = myUserRepository;
        this.myUserService = myUserService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void acceptInvitation(@NotNull String token, MyUser user) {
        adminInvitationRepository.findByToken(token)
                .orElseThrow(()-> new NoInvitationException("No invitation found"));
        adminInvitationRepository.deleteByToken(token);
        myUserService.registerUser(user, RoleEnum.ADMIN);
    }
}


