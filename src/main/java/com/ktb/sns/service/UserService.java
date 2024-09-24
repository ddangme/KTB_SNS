package com.ktb.sns.service;

import com.ktb.sns.dto.UserDTO;
import com.ktb.sns.dto.UserSession;
import com.ktb.sns.model.User;
import com.ktb.sns.model.constants.UserProvider;
import com.ktb.sns.model.constants.UserRole;
import com.ktb.sns.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserSession findUserByKakaoUserProvider(String providerId, String nickname) {
        Optional<User> user = userRepository.findByUserProviderAndProviderId(UserProvider.KAKAO, providerId);

        if (user.isEmpty()) {
            return saveKakaoUser(providerId, nickname, UserProvider.KAKAO);
        }

        updateNickname(user.get(), nickname);
        return UserSession.from(user.get());
    }

    @Transactional
    protected void updateNickname(User user, String nickname) {
        if (!user.getNickname().equals(nickname)) {
            user.setNickname(nickname);
        }
    }

    public Optional<UserDTO> searchUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .map(UserDTO::from);
    }

    @Transactional
    public UserSession saveKakaoUser(String providerId, String nickname, UserProvider provider) {
        return UserSession.from(userRepository.save(new User(providerId, nickname, provider)));
    }

    @Transactional
    public UserDTO saveUser(String loginId, String password, String email, String nickname) {
        return UserDTO.from(userRepository.save(User.of(loginId, password, email, nickname, UserRole.USER)));
    }

}
