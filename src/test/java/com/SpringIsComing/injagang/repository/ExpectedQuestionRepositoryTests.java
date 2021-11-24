package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Entity.ExpectedQuestion;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.EssayContentRepository;
import com.SpringIsComing.injagang.Repository.ExpectedQuestionRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ExpectedQuestionRepositoryTests {
    @Autowired
    private ExpectedQuestionRepository eqRepository;
    @Autowired
    private EssayContentRepository ecRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertExpectedQuestions() {
        //모든 EssayContent에 대하여 한 개의 예상질문 생성
        IntStream.rangeClosed(1,300).forEach(i -> {
            EssayContent essayContent = ecRepository.findById((long)i).get();
            long member_id = (long)(Math.random()*50) + 1;
            Member member = memberRepository.findById(member_id).get();

            ExpectedQuestion question = ExpectedQuestion.builder()
                    .text(".....sample 예상질문.....")
                    .writer(member)
                    .essayContent(essayContent)
                    .build();

            eqRepository.save(question);
        });
    }
}
