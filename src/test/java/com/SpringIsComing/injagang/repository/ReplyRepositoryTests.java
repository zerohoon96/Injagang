package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.Reply;
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
    private MemberRepository memberRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long member_id = (long)(Math.random()*50) + 1;
            long essay_id = (long)(Math.random()*250) + 1;
            Member member = memberRepository.findById(member_id).get();
            Essay essay = Essay.builder().id(essay_id).build();

            Reply reply = Reply.builder()
                    .content("Reply......" + i)
                    .essay(essay)
                    .replyer(member)
                    .build();

            replyRepository.save(reply);
        });
    }
}
