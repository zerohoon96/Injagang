package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayTemplateContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ETC_ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ET_ID")
    private EssayTemplate essayTemplate;

    private String question;

    public static EssayTemplateContent createEssayTemplateContent(String question) {
        return EssayTemplateContent.builder()
                .question(question)
                .build();
    }

    public void addEssayTemplate(EssayTemplate essayTemplate) {
        this.essayTemplate = essayTemplate;
    }
}
