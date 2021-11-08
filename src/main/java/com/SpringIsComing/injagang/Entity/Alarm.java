package com.SpringIsComing.injagang.Entity;
//알림 Entity

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue
    @Column(name = "ALARM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String content; //내용
    private LocalDateTime date; //날짜
}
