package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.InterviewFeedback;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.InterviewFeedbackRepository;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class InterviewFeedbackRepositoryTests {
    @Autowired
    private InterviewFeedbackRepository interviewFeedbackRepository;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertInterviewFeedbacks() {
        //300개의 피드백을 무작위 면접 게시물에 생성
        //300개의 피드백을 무작위 자소서 게시물에 생성
        IntStream.rangeClosed(1,300).forEach(i -> {
            long interview_id = (long)(Math.random()*150) + 1;
            long member_id = (long)(Math.random()*50) + 1;
            Interview interview = interviewRepository.findById(interview_id).get();
            Member member = memberRepository.findById(member_id).get();

            InterviewFeedback interviewFeedback = InterviewFeedback.builder()
                    .comment("Sample feedback..."+i)
                    .member(member)
                    .interview(interview)
                    .build();

            interviewFeedbackRepository.save(interviewFeedback);
        });
    }
}
