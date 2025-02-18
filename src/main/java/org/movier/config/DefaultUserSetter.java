package org.movier.config;

import jakarta.annotation.PostConstruct;
import org.movier.model.entity.MyUser;
import org.movier.model.enums.RoleEnum;
import org.movier.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class DefaultUserSetter {
    private final PasswordEncoder passwordEncoder;
    private final MyUserRepository myUserRepository;

    @Value("${admin.password}")
    private String password ;

    public DefaultUserSetter(PasswordEncoder passwordEncoder, MyUserRepository myUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.myUserRepository = myUserRepository;
    }

    @PostConstruct
    public void initDefaultUsers() {
        if(myUserRepository.findByUsernameIgnoreCase("OFFICIAL-MAIN-ADMIN").isEmpty()) {
            addDefaultAdmin();
        }
    }

    private void addDefaultAdmin() {
        MyUser myUser = new MyUser();
        myUser.setRole(RoleEnum.ADMIN);
        myUser.setPassword(passwordEncoder.encode(password));
        myUser.setEmail("admin@mail.com");
        myUser.setUsername("OFFICIAL-MAIN-ADMIN");
        myUser.setEmailActivated(true);
        myUserRepository.save(myUser);
    }
}
