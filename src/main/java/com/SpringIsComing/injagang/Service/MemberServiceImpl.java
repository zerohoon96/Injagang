package com.SpringIsComing.injagang.Service;


import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.MemberType;
import com.SpringIsComing.injagang.Repository.AuthTokenRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import com.SpringIsComing.injagang.token.AuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthTokenRepository authTokenRepository;
    private final PasswordEncoder encoder;


    @Override
    public Long save(RegisterDTO registerDTO) {

        String digest = encoder.encode(registerDTO.getPassword());


        Member saveMember = Member.builder()
                .loginId(registerDTO.getLoginId())
                .password(digest)
                .name(registerDTO.getName())
                .nickname(registerDTO.getNickname())
                .email(registerDTO.getEmail())
                .type(MemberType.GUEST)
                .build();

        Member saved = memberRepository.save(saveMember);

        return saved.getId();
    }

    @Override
    public Member login(String loginId, String password) {

        Member member = memberRepository.findByLoginId(loginId)
                .filter(m -> encoder.matches(password,m.getPassword()))
                .orElse(null);

        return member;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean loginDuplicateCheck(String loginId) {

        if (memberRepository.findByLoginId(loginId).isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean nicknameDuplicateCheck(String nickname) {
        if (memberRepository.findByNickname(nickname) == null) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean emailDuplicateCheck(String email) {

        if (memberRepository.findByEmail(email).isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public void confirmEmail(String token) {

        AuthToken findToken = authTokenRepository.findByIdAndExpirationDateAfterAndExpired(token, LocalDateTime.now(), false)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 토근"));

        Member findMember = memberRepository.findByLoginId(findToken.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다"));

            findToken.useToken();
            findMember.authSuccess();

    }

    @Override
    @Transactional(readOnly = true)
    public Member confirmEmailForPassword(String token) {
        AuthToken findToken = authTokenRepository.findByIdAndExpirationDateAfterAndExpired(token, LocalDateTime.now(), false)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 토근"));

        Member findMember = memberRepository.findByLoginId(findToken.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다"));
        findToken.useToken();

        return findMember;
    }

    @Override
    public Boolean passwordCheck(Member member, String password) {


        return encoder.matches(password, member.getPassword());
    }

    @Override
    public void changePassword(String loginId, String password) {
        Member findMember = memberRepository.findByLoginId(loginId).get();

        String digest = encoder.encode(password);

        findMember.changePassword(digest);

    }

    @Override
    public void changeNickname(String nowNickname, String changeNickname) {

        Member finMember = memberRepository.findByNickname(nowNickname);

        finMember.changeNickname(changeNickname);


    }
//    @Override
//    public void addFriend(String loginNickname, String targetNickname) {
//        Member loginMember = memberRepository.findByNickname(loginNickname);
//        Member targetMember = memberRepository.findByNickname(targetNickname);
//
//
//        loginMember.addFriend(targetMember);
//
//
//    }

    @Override
    @Transactional(readOnly = true)
    public Member findByEmail(String email) {

        return memberRepository.findByEmail(email).orElse(null);

    }

    @Override
    public Member findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    @Override
    public Member findByLoginId(String loginId) {

        return memberRepository.findByLoginId(loginId).orElseThrow(
                () -> new RuntimeException("없는 아이디입니다.")
        );

    }

    @Override
    public Member findById(Long Id) {
        return memberRepository.findById(Id).orElseThrow(
                () -> new IllegalArgumentException("멤버 없당--")
        );
    }

    @Override
    public boolean isAdmin(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        if(member.getType() == MemberType.ADMIN)
            return true;
        else
            return false;
    }


}
