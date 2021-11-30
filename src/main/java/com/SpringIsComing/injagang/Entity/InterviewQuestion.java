package com.SpringIsComing.injagang.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERVIEW_QUESTION_ID")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
}
