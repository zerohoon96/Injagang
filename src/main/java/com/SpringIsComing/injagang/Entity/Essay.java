package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//자기소개서 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Essay{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESSAY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member writer;

    @OneToMany(mappedBy = "essay", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Reply> replies = new ArrayList<>(); //달린 댓글들

    @OneToMany(mappedBy = "essay", cascade = CascadeType.ALL)
    @Builder.Default
    private List<EssayContent> contents =  new ArrayList<>(); //자소서 문항들

    @OneToMany(mappedBy = "essay", cascade = CascadeType.ALL)
    @Builder.Default
    private List<EssayFeedback> feedbacks = new ArrayList<>(); //피드백들

    private Integer access; //공개 범위(0,null: private, 1: 친구공개, 2:게시판 등록)

    private String essayTitle; //자소서 제목

    private String templateTitle; //템플릿 제목

    private String title; //제목

    @Column(length = 1500)
    private String text; //내용 글

    private LocalDateTime date; //최신 작성 시간

    //공개범위 수정
    public void setAccess(int val){
        this.access = val;
    }

    //게시물 제목 수정
    public void setTitle(String str){
        this.title = str;
    }

    //게시물 내용 글 수정
    public void setText(String str){
        this.text = str;
    }

    //게시물 최신 수정 시간 수정
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    //자소서에 달린 댓글들 모두 삭제
    public void clearReplies(){
        this.replies = new ArrayList<>();
    }

    //자소서에 달린 피드백들 모두 삭제
    public void clearFeedbacks(){
        this.feedbacks = new ArrayList<>();
    }

    public static Essay createEssay(String essayTitle, String title, List<EssayContent> EssayContents, Member member) {
        Essay essay = Essay.builder()
                .essayTitle(essayTitle)
                .templateTitle(title)
                .writer(member)
                .date(LocalDateTime.now())
                .build();

        for (EssayContent essayContent : EssayContents) {
            // 담고나서 식별을 위해 fk를 달아줘야함
            essay.addEssayContent(essayContent);
//            essay.contents.add(essayContent);
        }
        return essay;
    }

    //this는 실행한 객체 essay
    public void addEssayContent(EssayContent essayContent) {
        contents.add(essayContent);
        //역으로도 연결시켜줘야함
        essayContent.addEssay(this);
    }
}
