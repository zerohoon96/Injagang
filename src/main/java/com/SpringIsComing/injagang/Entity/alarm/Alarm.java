package com.SpringIsComing.injagang.Entity.alarm;
//알림 Entity

import com.SpringIsComing.injagang.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public abstract class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALARM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String content; //내용

    private LocalDateTime date; //날짜

    private boolean read_check = false;


    public Alarm(Member member, String content, LocalDateTime date) {
        this.member = member;
        this.content = content;
        this.date = date;
    }

    public void alarmRead(){
        this.read_check = true;
    }
}
