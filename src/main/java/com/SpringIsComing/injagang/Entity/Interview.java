package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//면접 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERVIEW_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member writer;

    @OneToMany(mappedBy = "interview")
    @Builder.Default
    private List<Reply> replies = new ArrayList<>(); //달린 댓글들

    @OneToMany(mappedBy = "interview")
    @Builder.Default
    private List<Video> videos = new ArrayList<>(); //면접 녹화 영상들

    private String title; //제목
    private String text; //내용 글
    private LocalDateTime date; //작성 날짜

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }

    public void clearReplies(){
        this.replies = new ArrayList<>();
    }
}
