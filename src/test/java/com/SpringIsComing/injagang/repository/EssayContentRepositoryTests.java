package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.EssayContent;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Repository.EssayContentRepository;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class EssayContentRepositoryTests {

    @Autowired
    private EssayRepository essayRepository;
    @Autowired
    private EssayContentRepository essayContentRepository;

    @Test
    public void insertEssayContents() {
        IntStream.rangeClosed(1,150).forEach(i -> {
            Essay essay = essayRepository.findById((long)i).get();

            //각 자소서 게시물 당 3개의 항목
            IntStream.rangeClosed(1,3).forEach(j -> {
                EssayContent essayContent = EssayContent.builder()
                        .title("----------질문----------")
                        .text("==========답변==========")
                        .essay(essay)
                        .build();

                essayContentRepository.save(essayContent);
            });
        });
    }
}
