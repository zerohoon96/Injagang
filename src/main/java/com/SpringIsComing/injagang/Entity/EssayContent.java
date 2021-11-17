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
    @GeneratedValue
    @Column(name = "EssayContent_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Essay essay;

    @OneToMany(mappedBy = "essayContent")
    @Builder.Default
    private List<ExpectedQuestion> questions = new ArrayList<>();

    private String title; //질문
    private String content; //답변
}
