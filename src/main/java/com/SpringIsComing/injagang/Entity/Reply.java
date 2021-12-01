package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//댓글 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESSAY_ID")
    private Essay essay; //자소서 게시물에 달린 댓글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INTERVIEW_ID")
    private Interview interview; //면접 게시물에 달린 댓글

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member replyer;

    @Column(length = 2000)
    private String content; //내용

    private LocalDateTime date; //글쓴 날짜
}
