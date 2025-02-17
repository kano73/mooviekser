package org.movier.model.details;

import org.movier.model.entity.MyUser;
import org.movier.repository.BannedRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final MyUser myUser;
    private final BannedRepository bannedRepository;

    public MyUserDetails(MyUser myUser, BannedRepository bannedRepository) {
        this.myUser = myUser;
        this.bannedRepository = bannedRepository;
    }

    @Override
    public String getPassword() {
        return myUser.getPassword();
    }

    @Override
    public String getUsername() {
        return myUser.getUsername();
    }

    public String getEmail() {return myUser.getEmail();}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + myUser.getRole().name()));
    }

    @Override
    public boolean isEnabled() {
        return myUser.getEmailActivated();
    }

    @Override
    public boolean isAccountNonLocked() {
        return bannedRepository.findByUser(myUser).isEmpty();
    }


    public MyUser getMyUser() {
        return myUser;
    }


}
