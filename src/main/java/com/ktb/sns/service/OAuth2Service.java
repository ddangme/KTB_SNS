package com.ktb.sns.service;

import com.ktb.sns.dto.security.KakaoTokenResponse;
import com.ktb.sns.dto.security.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service {

    @Value("${kakao.auth.client-id}")
    private String clientId;

    @Value("${kakao.auth.client-secret}")
    private String clientSecret;

    @Value("${kakao.auth.redirect-uri}")
    private String redirectUri;

    public KakaoUserInfoResponse kakaoLoginAPI(String code) {
        KakaoTokenResponse kakaoTokenResponse = getKakaoAccessToken(code);
        return getKakaoUserInfo(kakaoTokenResponse.accessToken());
    }

    private KakaoTokenResponse getKakaoAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("charset", "utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        log.info(clientId);
        log.info(redirectUri);
        log.info(clientSecret);
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("client_secret", clientSecret);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<KakaoTokenResponse> response = restTemplate.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    requestEntity,
                    KakaoTokenResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            log.error("Failed to get Kakao access token: ", e);
            throw new RuntimeException("Failed to get Kakao access token: ", e);
        }
    }

    private KakaoUserInfoResponse getKakaoUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.setBearerAuth(accessToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.GET,
                    requestEntity,
                    KakaoUserInfoResponse.class
            );

            return response.getBody();

        } catch (Exception e) {
            log.error("Failed to get Kakao user info: ", e);
            throw new RuntimeException("Failed to get Kakao user info: ", e);
        }

    }

}
