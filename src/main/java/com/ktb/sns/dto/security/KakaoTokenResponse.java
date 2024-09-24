package com.ktb.sns.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoTokenResponse(
        @JsonProperty("access_token")
                String accessToken
) {

}
