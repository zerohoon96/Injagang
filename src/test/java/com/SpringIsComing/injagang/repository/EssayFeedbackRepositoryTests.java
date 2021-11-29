package com.SpringIsComing.injagang.repository;


import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayFeedback;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.EssayFeedbackRepository;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class EssayFeedbackRepositoryTests {

    @Autowired
    private EssayFeedbackRepository essayFeedbackRepository;
    @Autowired
    private EssayRepository essayRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertEssayFeedbacks() {

        IntStream.rangeClosed(1,150).forEach(i -> {
            long essay_id = (long)(Math.random()*100) + 1;
            long member_id = (long)(Math.random()*51) + 1;
            Essay essay = essayRepository.findById(essay_id).get();
            Member member = memberRepository.findById(member_id).get();

            EssayFeedback essayFeedback = EssayFeedback.builder()
                    .content("Sample feedback..."+i)
                    .essay(essay)
                    .member(member)
                    .date(LocalDateTime.now())
                    .build();

            essayFeedbackRepository.save(essayFeedback);
        });
    }

}
