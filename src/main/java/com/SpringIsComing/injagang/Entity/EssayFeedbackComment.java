package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//자기소개서 첨삭 댓글 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayFeedbackComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EFC_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EF_ID")
    private EssayFeedback essayFeedback;

    private int num; //문항 번호
    private int startIdx; //시작 인덱스
    private int endIdx; //종료 인덱스
    private String content; //피드백 내용
}