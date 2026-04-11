package com.hospital.service;

import com.hospital.entity.RefreshToken;
import com.hospital.entity.User;
import com.hospital.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {


    private final RefreshTokenRepository  refreshTokenRepository;


    public RefreshToken save(RefreshToken refreshToken){
       return refreshTokenRepository.save(refreshToken);
    }


    public static boolean isRefreshExpired(RefreshToken token) {
        return token.getExpireDate().before(new Date());
    }

    public  RefreshToken findByUser(User user){
        return refreshTokenRepository.getRefreshTokenByUser(user)
                .orElse(null);
    }



    public Optional<RefreshToken> findByRefreshToken(String refreshToken){
        Optional<RefreshToken> refreshTokenDB=
                refreshTokenRepository.findRefreshTokenByRefreshToken(refreshToken);
        if(refreshTokenDB.isEmpty()){
            throw new RuntimeException("Invalid refresh token");
        }
        if(RefreshTokenService.isRefreshExpired(refreshTokenDB.get())){
            throw new RuntimeException("Refresh Token is expired");
        }

        return refreshTokenDB;
    }



}
