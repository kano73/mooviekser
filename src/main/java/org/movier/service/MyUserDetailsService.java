package org.movier.service;

import org.movier.model.details.MyUserDetails;
import org.movier.model.entity.MyUser;
import org.movier.repository.MyUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    public MyUserDetailsService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        MyUser myUser = myUserRepository.findByUsernameIgnoreCase(username).orElseThrow(
                                                    () -> new UsernameNotFoundException("no user found with such username")
                );

        return new MyUserDetails(myUser);
    }
}

