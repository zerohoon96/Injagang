package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//회원 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String loginId; //아이디

    private String password; //비밀번호

    private String name;

    @Column(unique = true)
    private String nickname;

    private String email;

    private boolean auth = false;

    public void changePassword(String password) {
        this.password = password;
    }


    public void authSuccess() {
        this.auth = true;
    }




}
