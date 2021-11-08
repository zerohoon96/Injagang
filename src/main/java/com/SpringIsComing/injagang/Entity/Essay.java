package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

//자기소개서 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Essay extends Board{

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title; //제목
    @OneToMany(mappedBy = "essay")
    private List<EssayContent> contents =  new ArrayList<>(); //자소서 문항들
}
