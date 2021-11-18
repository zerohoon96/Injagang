package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//자기소개서 각 항목 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EssayContent_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESSAY_ID")
    private Essay essay;

    @OneToMany(mappedBy = "essayContent")
    @Builder.Default
    private List<ExpectedQuestion> questions = new ArrayList<>();

    private String question; //질문
    private String answer; //답변
    private boolean template; //템플릿 설정(true : 템플릿, false : 직접읿력)


    public static EssayContent createEssayContent(String question, String answer, boolean template) {
        return EssayContent.builder()
                .question(question)
                .answer(answer)
                .template(template)
                .build();
    }

    public void addEssay(Essay essay) {
        this.essay = essay;
    }

}
