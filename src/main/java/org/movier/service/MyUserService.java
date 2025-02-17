package org.movier.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.movier.config.security.AuthenticatedMyUserService;
import org.movier.exceptions.*;
import org.movier.model.dto.MyUserChangeDTO;
import org.movier.model.entity.Banned;
import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.repository.BannedRepository;
import org.movier.repository.MyUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserService {
    private final MyUserRepository myUserRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticatedMyUserService authenticatedMyUserService;
    private final BannedRepository bannedRepository;

    public MyUserService(MyUserRepository myUserRepository, UserValidator userValidator, PasswordEncoder passwordEncoder, EmailService emailService, AuthenticatedMyUserService authenticatedMyUserService, BannedRepository bannedRepository) {
        this.myUserRepository = myUserRepository;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.authenticatedMyUserService = authenticatedMyUserService;
        this.bannedRepository = bannedRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = {Exception.class,  RuntimeException.class})
    public boolean registerUser(MyUser user) {
        userValidator.validateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleEnum.USER);
        user.setEmailActivated(false);
        myUserRepository.save(user);
        try {
            emailService.prepareEmailValidation(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Transactional
    public boolean verifyEmail(String token) {
        MyUser user = emailService.findByToken(token);
        if (user!=null) {
            myUserRepository.updateUserEmailActivatedById(user.getId(), true);
            emailService.deleteByUser(user);
            return true;
        }else{
            return false;
        }

    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public boolean updateUser(@Valid MyUserChangeDTO dto) {
        MyUser user = authenticatedMyUserService.getCurrentUserAuthenticated();
        boolean updated = false;

        if(!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IncorrectCredentialsException("Old password is not correct");
        }
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            updated = true;
        }
        if (dto.getEmail() != null) {
            if(myUserRepository.findByEmailIgnoreCase(dto.getEmail()).isPresent()) {
                throw new EmailIsInUseException("Email is owned by another user");
            }
            user.setEmail(dto.getEmail());
            user.setEmailActivated(false);
            emailService.prepareEmailValidation(user);

            updated = true;
        }
        if (dto.getUsername() != null) {
            if(myUserRepository.findByUsernameIgnoreCase(dto.getUsername()).isPresent()) {
                throw new UsernameIsInUseException("Username is owned by another user");
            }
            user.setUsername(dto.getUsername());
            updated = true;
        }

        if (updated) {
            myUserRepository.save(user);
            return true;
        }else{
            throw new NoChangesFoundException("No fileds to update");
        }
    }

    public boolean addAdminByUsername(@NotNull String username) {
        MyUser user = myUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(()-> new MyUserNotFoundException("No user found with username: "+username));

        user.setRole(RoleEnum.ADMIN);
        myUserRepository.save(user);
        return true;
    }

    public boolean banUserByUsername(@NotNull String username) {
        MyUser user = myUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(()-> new MyUserNotFoundException("No user found with username: "+username));

        if(bannedRepository.findByUser(user).isPresent()) {
            throw new UserAlreadyBannedException("user : "+user.getUsername()+" already banned.");
        }

        Banned ban = new Banned();
        ban.setUser(user);
        bannedRepository.save(ban);
        return true;
    }

    public boolean unbanUserByUsername(@NotNull String username) {
        MyUser user = myUserRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(()-> new MyUserNotFoundException("No user found with username: "+username));

        Banned ban = bannedRepository.findByUser(user)
                .orElseThrow(()-> new MyUserNotFoundException("User is not banned: "+username));

        bannedRepository.delete(ban);
        return true;
    }
}
