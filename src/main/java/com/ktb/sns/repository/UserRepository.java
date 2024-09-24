package com.ktb.sns.repository;

import com.ktb.sns.model.User;
import com.ktb.sns.model.constants.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginId(String loginId);
    Optional<User> findByUserProviderAndProviderId(UserProvider userProvider, String providerId);
}
