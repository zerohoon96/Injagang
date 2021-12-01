package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.InterviewQuestion;
import com.SpringIsComing.injagang.Entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion,Long> {

    int countByQuestionType(Enum<QuestionType> type);

}
