package com.checker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nickname", length = 30, nullable = false)
    private String nickname;

    @Column(name = "password", length = 96, nullable = false)
    private String password;

    public Student(String nickname, String password) {
        this.nickname = nickname;
        this.password = new Argon2PasswordEncoder().encode(password);
    }

    protected Student() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}