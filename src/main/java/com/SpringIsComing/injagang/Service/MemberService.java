package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.MypageFriendDTO;
import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.DTO.UpdateDTO;
import com.SpringIsComing.injagang.Entity.Member;

public interface MemberService {
    Long save(RegisterDTO registerDTO);

    Member login(String loginId, String password);

    Boolean loginDuplicateCheck(String loginId);

    Boolean nicknameDuplicateCheck(String nickname);

    Boolean emailDuplicateCheck(String email);

    void confirmEmail(String token);
    Member confirmEmailForPassword(String token);


    Boolean passwordCheck(Member member, String password);

    void changePassword(String nickname, String password);

    Member findByEmail(String email);

    Member findByNickname(String nickname);

    Member findByLoginId(String loginId);

    Member findById(Long Id);



    void changeNickname(String nowNickname, String changeNickname);


    default UpdateDTO toUpdateDTO(Member member) {

        return UpdateDTO.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .name(member.getName())
                .build();

    }


}
