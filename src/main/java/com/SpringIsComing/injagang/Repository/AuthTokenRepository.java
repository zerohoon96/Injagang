package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.token.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthTokenRepository extends JpaRepository<AuthToken,String> {

    Optional<AuthToken> findByIdAndExpirationDateAfterAndExpired(String authTokenId, LocalDateTime now, boolean expired);


}
