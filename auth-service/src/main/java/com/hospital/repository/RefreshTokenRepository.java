package com.hospital.repository;

import com.hospital.entity.RefreshToken;
import com.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> getRefreshTokenByUser(User user);

    Optional<RefreshToken> findRefreshTokenByRefreshToken(String refreshToken);

}
