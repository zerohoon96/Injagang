package com.SpringIsComing.injagang.Service;

import com.SpringIsComing.injagang.Entity.InterviewQuestion;
import com.SpringIsComing.injagang.Repository.InterviewQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterviewQuestionService {

    private final InterviewQuestionRepository interviewQuestionRepository;

    public Long save(InterviewQuestion interviewQuestion){
        InterviewQuestion save = interviewQuestionRepository.save(interviewQuestion);

        return save.getId();
    }


    public List<InterviewQuestion> findAll() {
        return interviewQuestionRepository.findAll();
    }

    public void addInterviewQuestions(InterviewQuestion question){

        interviewQuestionRepository.save(question);
    }

    public void deleteInterviewQuestions(Long questionId) {

        interviewQuestionRepository.deleteById(questionId);

    }

}
