package com.ktb.sns.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserInfoResponse(
        @JsonProperty("id")
        String providerId,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            Profile profile
    ) {
        public record Profile(
                String nickname
        ) {}
    }

    public String nickname() {
        return this.kakaoAccount.profile.nickname;
    }
}