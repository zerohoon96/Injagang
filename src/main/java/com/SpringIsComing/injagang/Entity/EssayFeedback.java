package com.SpringIsComing.injagang.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//자기소개서 첨삭 Entity
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayFeedback {
    @Id
    @GeneratedValue
    @Column(name = "EF_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Essay essay; //FK

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //FK

    @OneToMany(mappedBy = "essayFeedback")
    private List<EssayFeedbackComment> feedbackComments= new ArrayList<>();

    private String content; //총평

    LocalDateTime date; //글쓴 날짜
}
