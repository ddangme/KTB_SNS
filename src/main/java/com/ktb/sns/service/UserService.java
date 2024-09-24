package com.ktb.sns.service;

import com.ktb.sns.dto.UserDTO;
import com.ktb.sns.model.User;
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

    public Optional<UserDTO> searchUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .map(UserDTO::from);
    }

    @Transactional
    public UserDTO saveUser(String loginId, String password, String email, String nickname) {
        return UserDTO.from(userRepository.save(User.of(loginId, password, email, nickname, UserRole.USER)));
    }

}
