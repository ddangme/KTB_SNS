package com.ktb.sns.model.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserProvider {
    KAKAO("KAKAO"),
    ;
    private final String provider;
}
