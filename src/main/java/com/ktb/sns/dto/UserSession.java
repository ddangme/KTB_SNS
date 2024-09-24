package com.ktb.sns.dto;

import com.ktb.sns.model.User;
import com.ktb.sns.model.constants.UserProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSession {
    private Long id;
    private String providerId;
    private String nickname;
    private UserProvider userProvider;

    public static UserSession from(User entity) {
        return new UserSession(
                entity.getId(),
                entity.getProviderId(),
                entity.getNickname(),
                entity.getUserProvider()
        );
    }
}
