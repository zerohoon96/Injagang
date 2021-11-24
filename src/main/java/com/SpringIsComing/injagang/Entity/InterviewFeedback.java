package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//면접 첨삭 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IF_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VIDEO_ID")
    private Video video;

    private String comment; //피드백 내용
    private LocalDateTime date; //글쓴 날짜
}
