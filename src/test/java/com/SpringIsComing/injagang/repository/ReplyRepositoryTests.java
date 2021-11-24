package com.SpringIsComing.injagang.repository;

import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Entity.Interview;
import com.SpringIsComing.injagang.Entity.Member;
import com.SpringIsComing.injagang.Entity.Reply;
import com.SpringIsComing.injagang.Repository.EssayRepository;
import com.SpringIsComing.injagang.Repository.InterviewRepository;
import com.SpringIsComing.injagang.Repository.MemberRepository;
import com.SpringIsComing.injagang.Repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private EssayRepository essayRepository;
    @Autowired
    private InterviewRepository interviewRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertEssayBoardReply() {
        //랜덤한 자소서 게시물들에 대하여 총 200개의 댓글 생성
        IntStream.rangeClosed(1, 200).forEach(i -> {
            long essay_id = (long)(Math.random()*100) + 1;
            long member_id = (long)(Math.random()*50) + 1;
            Essay essay = essayRepository.findById(essay_id).get();
            Member member = memberRepository.findById(member_id).get();

            Reply reply = Reply.builder()
                    .content("......Sample Reply......" + i)
                    .essay(essay)
                    .replyer(member)
                    .date(LocalDateTime.now())
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    public void insertInterviewBoardReply(){
        //랜덤한 면접 게시물들에 대하여 총 200개의 댓글 생성
        IntStream.rangeClosed(1, 200).forEach(i -> {
            long interview_id = (long)(Math.random()*100) + 1;
            long member_id = (long)(Math.random()*50) + 1;
            Interview interview = interviewRepository.findById(interview_id).get();
            Member member = memberRepository.findById(member_id).get();

            Reply reply = Reply.builder()
                    .content("......Sample Reply......" + i)
                    .interview(interview)
                    .replyer(member)
                    .date(LocalDateTime.now())
                    .build();

            replyRepository.save(reply);
        });
     }
}
