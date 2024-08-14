package com.pallow.pallow.domain.user.repository;

import com.pallow.pallow.domain.user.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByNickname(String nickname);

    Optional<User> findBykakaoId(Long id);
}
