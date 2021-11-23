package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class InterviewRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private InterviewRepository interviewRepository;

    @Test
    public void insertInterviews() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            long member_id = (long)(Math.random()*50) + 1;
            Member member = memberRepository.findById(member_id).get();

            Interview interview = Interview.builder()
                    .title("Interview Board Title..." + i)
                    .text("Interview Content..." + i)
                    .writer(member)
                    .date(LocalDateTime.now())
                    .interviewTitle("Sample Interview Title..."+i)
                    .access(2) //게시판에 올린 것으로 설정
                    .build();

            interviewRepository.save(interview);
        });

    }
}
