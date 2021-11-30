package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.InterviewQuestion;
import com.SpringIsComing.injagang.Repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;


    public List<InterviewQuestion> findAll() {
        return interviewQuestionRepository.findAll();
    }

    public void addInterviewQuestions(List<InterviewQuestion> questions){

        interviewQuestionRepository.saveAll(questions);
    }

    public void deleteInterviewQuestions(List<InterviewQuestion> questions) {

        interviewQuestionRepository.deleteAll(questions);

    }

}
