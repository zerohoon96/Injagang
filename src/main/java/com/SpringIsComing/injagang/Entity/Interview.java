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

//면접 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview extends Board{

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String title; //제목
    private String text; //글

    @OneToMany(mappedBy = "interview")
    private List<Video> videos = new ArrayList<>();
}
