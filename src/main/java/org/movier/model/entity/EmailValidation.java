package org.movier.model.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "email_validation",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "token"})
)
public class EmailValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    @Column(name="token", nullable = false)
    private String token;

    public EmailValidation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
