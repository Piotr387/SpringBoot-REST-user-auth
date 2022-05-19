package com.pp.app.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

    private static final long serialVersionUID = 3279170611212825088L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

    public PasswordResetTokenEntity() {
    }

    public PasswordResetTokenEntity(String token, UserEntity userEntity) {
        this.token = token;
        this.userEntity = userEntity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
