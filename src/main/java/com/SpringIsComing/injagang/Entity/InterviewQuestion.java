package com.SpringIsComing.injagang.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.*;

@Entity
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERVIEW_QUESTION_ID")
    private Long id;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
}
