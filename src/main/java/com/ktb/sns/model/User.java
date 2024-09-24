package com.ktb.sns.model;

import com.ktb.sns.model.constants.UserProvider;
import com.ktb.sns.model.constants.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String loginId;

    @Column(length = 500)
    private String password;

    @Column(nullable = false, length = 500)
    private String name;

    @Setter
    @Column(nullable = false, length = 500)
    private String nickname;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserProvider userProvider;

    @Column
    private String providerId;

    @CreatedDate
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public User(String providerId, String nickname, UserProvider userProvider) {
        this.name = nickname;
        this.providerId = providerId;
        this.userRole = UserRole.USER;
        this.userProvider = userProvider;
        this.nickname = nickname;
    }

    public User(String loginId, String password, String name, String email, String nickname, UserRole userRole) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public static User of(String loginId, String password, String email, String nickname, UserRole userRole) {
        return new User(loginId, password, nickname, email, nickname, userRole);
    }


}
