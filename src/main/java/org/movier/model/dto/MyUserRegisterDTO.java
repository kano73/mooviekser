package org.movier.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.movier.model.entity.MyUser;

public class MyUserRegisterDTO {

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    @Email
    private String email;

    public MyUserRegisterDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MyUserRegisterDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public MyUser toMyUser() {
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);
        myUser.setEmail(email);
        return myUser;
    }
}
