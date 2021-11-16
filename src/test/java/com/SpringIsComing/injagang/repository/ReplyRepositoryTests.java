package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.Reply;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import com.SpringIsComing.injagang.Repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private EssayRepository essayRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertReply() {
        //랜덤한 자소서 게시물들에 대하여 총 300개의 댓글 생성
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long essay_id = (long)(Math.random()*150) + 1;
            long member_id = (long)(Math.random()*50) + 1;
            Essay essay = essayRepository.findById(essay_id).get();
            Member member = memberRepository.findById(member_id).get();

            Reply reply = Reply.builder()
                    .content("Reply......" + i)
                    .essay(essay)
                    .replyer(member)
                    .build();

            replyRepository.save(reply);
        });
    }
}
