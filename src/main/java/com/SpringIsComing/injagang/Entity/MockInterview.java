package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//모의면접 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockInterview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MockInterviewId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String title; //면접 제목

    private Integer qCnt; //면접에서 본 질문 개수

    private LocalDateTime date;
}
