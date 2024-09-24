package com.ktb.sns.dto.security;

import com.ktb.sns.dto.UserDTO;
import com.ktb.sns.model.User;
import com.ktb.sns.model.constants.UserProvider;
import com.ktb.sns.model.constants.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record SnsPrincipal(
        String loginId,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname,
        String providerId,
        UserProvider userProvider,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static SnsPrincipal of(String providerId, UserProvider provider, String nickname) {
        return SnsPrincipal.of(nickname, null, null, nickname, Map.of(), provider, providerId);
    }

    public static SnsPrincipal of(String username, String password, String email, String nickname) {
        return SnsPrincipal.of(username, password, email, nickname, Map.of(), null, null);
    }

    public static SnsPrincipal of(String username, String password, String email, String nickname, Map<String, Object> oAuth2Attributes, UserProvider userProvider, String providerId) {
        // 지금은 인증만 하고 권한을 다루고 있지 않아서 임의로 세팅한다.
        Set<UserRole> roleTypes = Set.of(UserRole.USER);

        return new SnsPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(UserRole::getRole)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                providerId, userProvider,
                oAuth2Attributes
        );
    }

    public static SnsPrincipal from(User entity) {
        return SnsPrincipal.of(
                entity.getNickname(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getNickname(),
                Map.of(),
                entity.getUserProvider(),
                entity.getProviderId()
        );
    }

    public static SnsPrincipal from(UserDTO dto) {
        return SnsPrincipal.of(
                dto.loginId(),
                dto.password(),
                dto.email(),
                dto.nickname()
        );
    }

    public UserDTO toDTO() {
        return UserDTO.of(
                loginId,
                password,
                email,
                nickname
        );
    }

    @Override public String getUsername() { return nickname; }
    @Override public String getPassword() { return password; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    @Override public Map<String, Object> getAttributes() { return oAuth2Attributes; }
    @Override public String getName() { return nickname; }

}
