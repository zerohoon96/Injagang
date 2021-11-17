package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByLoginId(String loginId);

    Member findByNickname(String nickname);

    Optional<Member> findByEmail(String email);





}
