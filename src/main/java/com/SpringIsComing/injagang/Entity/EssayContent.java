package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Essay essay;

    private String title; //질문
    private String content; //답변
}
