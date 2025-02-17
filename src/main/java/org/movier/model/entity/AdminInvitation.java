package org.movier.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="admin_invite",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "token"}))
public class AdminInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="token", nullable=false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}
