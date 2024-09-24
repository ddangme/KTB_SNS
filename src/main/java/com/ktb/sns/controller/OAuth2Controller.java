package com.ktb.sns.controller;

import com.ktb.sns.dto.UserSession;
import com.ktb.sns.dto.security.KakaoUserInfoResponse;
import com.ktb.sns.service.OAuth2Service;
import com.ktb.sns.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/login/kakao")
@RequiredArgsConstructor
public class OAuth2Controller {
    private final UserService userService;
    private final OAuth2Service oAuth2Service;

    @GetMapping
    public ResponseEntity<UserSession> getToken(String code) {
        KakaoUserInfoResponse kakaoUserInfoResponse = oAuth2Service.kakaoLoginAPI(code);
        UserSession session = userService.findUserByKakaoUserProvider(kakaoUserInfoResponse.providerId(), kakaoUserInfoResponse.nickname());

        return ResponseEntity.ok().body(session);
    }
}
