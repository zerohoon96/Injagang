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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EF_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESSAY_ID")
    private Essay essay; //FK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //FK

    @OneToMany(mappedBy = "essayFeedback",cascade = CascadeType.ALL)
    @Builder.Default
    private List<EssayFeedbackComment> feedbackComments= new ArrayList<>();

    @Column(length = 2000)
    private String content; //총평

    private LocalDateTime date; //글쓴 날짜
}
