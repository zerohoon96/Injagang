package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.DTO.RegisterDTO;
import com.SpringIsComing.injagang.Entity.Member;

public interface MemberService {

    public Long save(RegisterDTO registerDTO);
    public Member login(String loginId, String password);
    public Boolean loginDuplicateCheck(String loginId);
    public Boolean nicknameDuplicateCheck(String nickname);
    public Boolean emailDuplicateCheck(String email);
    public void confirmEmail(String token);
    public Member confirmEmailForPassword(String token);

    public void changePassword(Long memberId, String password);

    public Member findByEmail(String email);
}
