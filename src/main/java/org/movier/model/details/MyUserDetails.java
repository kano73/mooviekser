package org.movier.model.details;

import org.movier.model.entity.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record MyUserDetails(MyUser myUser) implements UserDetails {

    @Override
    public String getPassword() {
        return myUser.getPassword();
    }

    @Override
    public String getUsername() {
        return myUser.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + myUser.getRole().name()));
    }

    @Override
    public boolean isEnabled() {
        return myUser.getEmailActivated();
    }

    public MyUser getMyUser() {
        return myUser;
    }
}
