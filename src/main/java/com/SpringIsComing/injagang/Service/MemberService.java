package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.DTO.UpdateDTO;
import com.SpringIsComing.injagang.Entity.Member;

public interface MemberService {

    public Long save(RegisterDTO registerDTO);
    public Member login(String loginId, String password);
    public Boolean loginDuplicateCheck(String loginId);
    public Boolean nicknameDuplicateCheck(String nickname);
    public Boolean emailDuplicateCheck(String email);
    public void confirmEmail(String token);
    public Member confirmEmailForPassword(String token);

    public void changePassword(String nickname, String password);

    public Member findByEmail(String email);
    public Member findByNickname(String nickname);

    public void changeNickname(String nowNickname, String changeNickname);


    default UpdateDTO toUpdateDTO(Member member) {

        return UpdateDTO.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();

    }



}
