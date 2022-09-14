package com.microservice.restservice.dto;

import com.microservice.restservice.auxillary.validator.ValidPassword;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user")

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "name")
    private String name;
    @Column(name = "access_level")
    private String accessLevel;
    @ValidPassword
    @Column(name = "password")
    private String password;
    @Email
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    private String email;
    @Column(name = "token_id")
    private String tokenId;



    public User(String uuid, String name, String accessLevel, String password, String email) {
        this.uuid = uuid;
        this.name = name;
        this.accessLevel = accessLevel;
        this.password = password;
        this.email = email;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
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

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(uuid, user.uuid) && Objects.equals(name, user.name) && Objects.equals(accessLevel, user.accessLevel) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(tokenId, user.tokenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, name, accessLevel, password, email, tokenId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", tokenId='" + tokenId + '\'' +
                '}';
    }
}
