package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class EssayRepositoryTests {

    @Autowired
    private EssayRepository essayRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertEssays(){
        IntStream.rangeClosed(1,150).forEach(i -> {
            Long member_id = (long)(Math.random()*50) + 1;
            Member member = memberRepository.findById(member_id).get();

            Essay essay = Essay.builder()
                    .title("Essay Title..."+i)
                    .text("Essay Content..."+i)
                    .writer(member)
                    .build();

            essayRepository.save(essay);
        });
    }
}
