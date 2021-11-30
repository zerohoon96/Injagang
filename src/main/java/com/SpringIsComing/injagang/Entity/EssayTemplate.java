package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//템플릿 추가 Entity

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ET_ID")
    private Long id;


    @OneToMany(mappedBy = "essayTemplate", cascade = CascadeType.ALL)
    @Builder.Default
    private List<EssayTemplateContent> contents =  new ArrayList<>(); //템플릿 문항들

    private String templateTitle;

    @Builder.Default
    private boolean checked = false;

    public static EssayTemplate createEssayTemplate(String templateTitle, List<EssayTemplateContent> essayTemplateContents) {
        EssayTemplate essayTemplate = EssayTemplate.builder()
                .templateTitle(templateTitle)
                .build();

        for (EssayTemplateContent essayTemplateContent : essayTemplateContents) {
            // 담고나서 식별을 위해 fk를 달아줘야함
            essayTemplate.addEssayTemplateContent(essayTemplateContent);
        }

        return essayTemplate;
    }

    //this는 실행한 객체 essay
    public void addEssayTemplateContent(EssayTemplateContent essayTemplateContent) {
        contents.add(essayTemplateContent);
        //역으로도 연결시켜줘야함
        essayTemplateContent.addEssayTemplate(this);
    }
}
