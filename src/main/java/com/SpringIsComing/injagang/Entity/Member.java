package com.SpringIsComing.injagang.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private MemberType type;

    @Builder.Default
    private boolean auth = false;

    @OneToMany(mappedBy = "writer")
    @Builder.Default
    private List<Essay> essays = new ArrayList<>();

    @OneToMany(mappedBy = "writer")
    @Builder.Default
    private List<Interview> interviews = new ArrayList<>();

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void authSuccess() {
        this.auth = true;
    }


//    public void addFriend(Member member) {
//        this.friends.add(member);
//        member.getFriends().add(this);
//    }

}
